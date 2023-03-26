package com.Me.ShiftBoard.Model;

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
