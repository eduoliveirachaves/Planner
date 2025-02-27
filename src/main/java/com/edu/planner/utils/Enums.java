package com.edu.planner.utils;

/**
 * Enums class.
 * This class is used to create enums.
 * It contains the Status, Role and Gender enums.
 * Used by: UserEntity (Role, Gender) , TaskEntity (Status).
 */

public class Enums {

    public enum Status {
        PENDING,
        COMPLETED
    }

    public enum Role {
        ADMIN,
        USER
    }

    public enum Gender {
        MALE,
        FEMALE
    }


}