package com.lunark.lunark.mapper;

import com.lunark.lunark.dto.AccountDto;
import com.lunark.lunark.model.Account;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountDtoMapper {
    private static ModelMapper modelMapper;

    @Autowired
    public AccountDtoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public static Account fromDTOtoAccount(AccountDto accountDto) {
        return modelMapper.map(accountDto, Account.class);
    }

    public static AccountDto fromAccountToDTO(Account account) {
        return modelMapper.map(account, AccountDto.class);
    }



}
