package com.Me.ShiftBoard.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

       private String street;
        private String city;
        private String State;
        private String country;
        private  String zipCode;

}
