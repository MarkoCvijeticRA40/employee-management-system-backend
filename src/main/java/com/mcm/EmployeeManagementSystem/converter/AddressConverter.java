package com.mcm.EmployeeManagementSystem.converter;

import com.mcm.EmployeeManagementSystem.entity.AddressEntity;
import com.mcm.EmployeeManagementSystem.model.Address;

import java.util.ArrayList;
import java.util.List;

public class AddressConverter implements GenericConverter<Address, AddressEntity> {
    @Override
    public Address toModel(AddressEntity addressEntity) {
        if (addressEntity != null) {
            Address address = new Address();
            address.setId(addressEntity.getId());
            address.setCountry(addressEntity.getCountry());
            address.setCity(addressEntity.getCity());
            address.setStreet(addressEntity.getStreet());
            address.setNumber(addressEntity.getNumber());

            return address;
        } else {
            return null;
        }
    }

    @Override
    public List<Address> toModel(List<AddressEntity> addressEntities) {
        List<Address> addresses = new ArrayList<>();
        for (AddressEntity addressEntity : addressEntities) {
            addresses.add(this.toModel(addressEntity));
        }

        return addresses;
    }

    @Override
    public AddressEntity toEntity(Address address) {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(address.getId());
        addressEntity.setCountry(address.getCountry());
        addressEntity.setCity(address.getCity());
        addressEntity.setStreet(address.getStreet());
        addressEntity.setNumber(address.getNumber());

        return addressEntity;
    }

    @Override
    public List<AddressEntity> toEntity(List<Address> addresses) {
        List<AddressEntity> addressEntities = new ArrayList<>();
        for (Address address : addresses) {
            addressEntities.add(this.toEntity(address));
        }

        return addressEntities;
    }
}
