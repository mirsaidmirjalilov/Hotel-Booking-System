package com.example.hotelbookingsystem.service.impl;

import com.example.hotelbookingsystem.entity.Room;
import com.example.hotelbookingsystem.entity.RoomInventory;
import com.example.hotelbookingsystem.entity.User;
import com.example.hotelbookingsystem.exception.HotelNotBelongException;
import com.example.hotelbookingsystem.exception.RoomNotFoundException;
import com.example.hotelbookingsystem.exception.UserNotFoundException;
import com.example.hotelbookingsystem.mapper.RoomInventoryMapper;
import com.example.hotelbookingsystem.payload.roomInventory_related.AvailabilityCheckRequest;
import com.example.hotelbookingsystem.payload.roomInventory_related.BulkInventoryRequest;
import com.example.hotelbookingsystem.payload.roomInventory_related.InventoryResponse;
import com.example.hotelbookingsystem.payload.roomInventory_related.InventoryUpdateRequest;
import com.example.hotelbookingsystem.repository.RoomInventoryRepository;
import com.example.hotelbookingsystem.repository.RoomRepository;
import com.example.hotelbookingsystem.repository.UserRepository;
import com.example.hotelbookingsystem.service.RoomInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomInventoryServiceImpl implements RoomInventoryService {
    private final RoomInventoryRepository roomInventoryRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final RoomInventoryMapper roomInventoryMapper;

    @Override
    public boolean isRoomAvailable(Long roomId, AvailabilityCheckRequest request) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("Room not found"));

        Map<LocalDate, RoomInventory> localDateRoomInventoryMap = fetchInventoryMap(roomId, request.checkIn(), request.checkOut());

        for (LocalDate date = request.checkIn(); !date.isAfter(request.checkOut()); date = date.plusDays(1)) {

            RoomInventory inventoryForDate = localDateRoomInventoryMap.get(date);

            int availableRooms;
            if (inventoryForDate != null && inventoryForDate.getAvailableRooms() != null) {
                availableRooms = inventoryForDate.getAvailableRooms();
            } else {
                availableRooms = room.getTotalRooms();
            }

            if (availableRooms < request.requestedRooms()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public List<InventoryResponse> getDailyAvailability(Long roomId, LocalDate startDate, LocalDate endDate) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("Room with ID " + roomId + " not found"));

        Map<LocalDate, RoomInventory> localDateRoomInventoryMap = fetchInventoryMap(roomId, startDate, endDate);
        List<RoomInventory> roomInventories = new ArrayList<>();

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            RoomInventory roomInventory = localDateRoomInventoryMap.get(date);

            if (roomInventory == null) {
                roomInventory = new RoomInventory();
                roomInventory.setRoom(room);
                roomInventory.setDate(date);
                roomInventory.setAvailableRooms(room.getTotalRooms());
            } else if (roomInventory.getAvailableRooms() == null) {
                roomInventory.setAvailableRooms(room.getTotalRooms());
            }

            roomInventories.add(roomInventory);
        }

        return roomInventories.stream()
                .map(roomInventoryMapper::toRoomInventoryResponse)
                .toList();
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('MANAGER')")
    public InventoryResponse updateInventory(Long managerId, Long roomId, InventoryUpdateRequest request) {
        User user = userRepository.findById(managerId).orElseThrow(() -> new UserNotFoundException("user not found"));
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RoomNotFoundException("Room not found"));

        if (!user.getHotels().contains(room.getHotel())) {
            throw new HotelNotBelongException("this hotel doesn't belong to this user");
        }

        RoomInventory roomInventory = roomInventoryRepository.findByRoomIdAndDate(roomId, request.date()).orElse(new RoomInventory());

        roomInventory.setAvailableRooms(request.availableRooms());
        roomInventory.setRoom(room);
        roomInventory.setDate(request.date());

        roomInventoryRepository.save(roomInventory);

        return roomInventoryMapper.toRoomInventoryResponse(roomInventory);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('MANAGER')")
    public List<InventoryResponse> bulkUpdateInventory(Long managerId, Long roomId, BulkInventoryRequest request) {
        User user = userRepository.findById(managerId).orElseThrow(() -> new UserNotFoundException("User not found"));
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RoomNotFoundException("Room not found"));

        if (!user.getHotels().contains(room.getHotel())) {
            throw new HotelNotBelongException("This hotel doesn't belong to this user");
        }

        List<RoomInventory> existingInventories = roomInventoryRepository
                .findByRoomIdAndDateBetween(roomId, request.startDate(), request.endDate());

        Map<LocalDate, RoomInventory> inventoryMap = existingInventories.stream()
                .collect(Collectors.toMap(RoomInventory::getDate, inventory -> inventory));

        List<RoomInventory> inventoriesToSave = new ArrayList<>();

        for (LocalDate date = request.startDate(); !date.isAfter(request.endDate()); date = date.plusDays(1)) {

            RoomInventory roomInventory = inventoryMap.get(date);
            if (roomInventory == null) {
                roomInventory = new RoomInventory();
                roomInventory.setRoom(room);
                roomInventory.setDate(date);
            }

            roomInventory.setAvailableRooms(request.availableRooms());

            inventoriesToSave.add(roomInventory);
        }

        roomInventoryRepository.saveAll(inventoriesToSave);

        return inventoriesToSave.stream()
                .map(roomInventoryMapper::toRoomInventoryResponse)
                .toList();
    }

    @Override
    @Transactional
    public void blockInventory(Long roomId, LocalDate checkIn, LocalDate checkOut, int roomsToBlock) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("Room with ID " + roomId + " not found"));

        Map<LocalDate, RoomInventory> inventoryMap = fetchInventoryMap(roomId, checkIn, checkOut);

        List<RoomInventory> updatedInventories = new ArrayList<>();

        for (LocalDate date = checkIn; !date.isAfter(checkOut); date = date.plusDays(1)) {

            RoomInventory roomInventory = inventoryMap.get(date);

            if (roomInventory == null) {
                roomInventory = new RoomInventory();
                roomInventory.setRoom(room);
                roomInventory.setDate(date);
                roomInventory.setAvailableRooms(room.getTotalRooms());
            }

            int remainingRooms = roomInventory.getAvailableRooms() - roomsToBlock;

            if (remainingRooms < 0) {
                throw new IllegalStateException("Not enough rooms available on " + date);
            }

            roomInventory.setAvailableRooms(remainingRooms);
            updatedInventories.add(roomInventory);
        }

        roomInventoryRepository.saveAll(updatedInventories);
    }

    @Override
    @Transactional
    public void releaseInventory(Long roomId, LocalDate checkIn, LocalDate checkOut, int roomsToRelease) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("Room with ID " + roomId + " not found"));

        Map<LocalDate, RoomInventory> inventoryMap = fetchInventoryMap(roomId, checkIn, checkOut);

        List<RoomInventory> updatedInventories = new ArrayList<>();

        for (LocalDate date = checkIn; !date.isAfter(checkOut); date = date.plusDays(1)) {

            RoomInventory roomInventory = inventoryMap.get(date);

            if (roomInventory == null) {
                roomInventory = new RoomInventory();
                roomInventory.setRoom(room);
                roomInventory.setDate(date);
                roomInventory.setAvailableRooms(room.getTotalRooms());
            } else {
                int newAvailability = roomInventory.getAvailableRooms() + roomsToRelease;

                if (newAvailability > room.getTotalRooms()) {
                    newAvailability = room.getTotalRooms();
                }

                roomInventory.setAvailableRooms(newAvailability);
            }

            updatedInventories.add(roomInventory);
        }

        roomInventoryRepository.saveAll(updatedInventories);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('MANAGER')")
    public List<InventoryResponse> initializeInventoryForRoom(Long roomId, int monthsAhead) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("Room with id: " + roomId + " not found"));

        LocalDate today = LocalDate.now();
        LocalDate maxFutureDate = today.plusMonths(monthsAhead);

        List<RoomInventory> existingInventories = roomInventoryRepository
                .findByRoomIdAndDateBetween(roomId, today, maxFutureDate);

        Map<LocalDate, RoomInventory> inventoryMap = existingInventories.stream()
                .collect(Collectors.toMap(RoomInventory::getDate, inventory -> inventory));

        List<RoomInventory> inventoriesToCreate = new ArrayList<>();

        for (LocalDate date = today; !date.isAfter(maxFutureDate); date = date.plusDays(1)) {
            if (!inventoryMap.containsKey(date)) {
                RoomInventory newInventory = new RoomInventory();
                newInventory.setRoom(room);
                newInventory.setDate(date);
                newInventory.setAvailableRooms(room.getTotalRooms());

                inventoriesToCreate.add(newInventory);
            }
        }

        List<RoomInventory> savedInventories = roomInventoryRepository.saveAll(inventoriesToCreate);

        List<RoomInventory> totalPeriodInventory = new ArrayList<>(existingInventories);
        totalPeriodInventory.addAll(savedInventories);

        totalPeriodInventory.sort(Comparator.comparing(RoomInventory::getDate));

        return totalPeriodInventory.stream()
                .map(roomInventoryMapper::toRoomInventoryResponse)
                .toList();
    }

    private Map<LocalDate, RoomInventory> fetchInventoryMap(Long roomId, LocalDate startDate, LocalDate endDate) {
        List<RoomInventory> roomInventories = roomInventoryRepository.findByRoomIdAndDateBetween(roomId, startDate, endDate);

        return roomInventories.stream()
                .collect(Collectors.toMap(RoomInventory::getDate, inventory -> inventory));
    }
}
