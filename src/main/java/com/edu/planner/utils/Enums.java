package com.edu.planner.utils;

/**
 * This class is used to create enums.
 * It contains the Status, Role and Gender enums.
 * Used by: UserEntity (Role, Gender) , Task (Status).
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
    
    public enum WeekDay {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRYDAY, SATURDAY, SUNDAY
    }
    
    public enum Frequency {
        DAILY, WEEKLY, MONTHLY
    }
    
    public enum TaskType {
        ONE_TIME, REPEATING
    }
}