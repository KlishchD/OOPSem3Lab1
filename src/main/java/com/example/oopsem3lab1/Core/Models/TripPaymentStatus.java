package com.example.oopsem3lab1.Core.Models;

import lombok.Getter;

@Getter
public enum TripPaymentStatus
{
    NotPaid(0),
    PaidPartially(1),
    PaidInFull(2);

    private final int value;

    TripPaymentStatus(int value)
    {
        this.value = value;
    }

    public static TripPaymentStatus parse(int value)
    {
        switch (value)
        {
            case 0: return TripPaymentStatus.NotPaid;
            case 1: return TripPaymentStatus.PaidPartially;
            case 2: return TripPaymentStatus.PaidInFull;
            default: throw new IllegalStateException("Unexpected value: " + value);
        }
    }

    @Override
    public String toString() {
        switch (value)
        {
            case 0: return "TripPaymentStatus.NotPaid";
            case 1: return "TripPaymentStatus.PaidPartially";
            case 2: return "TripPaymentStatus.PaidInFull";
            default: throw new IllegalStateException("Unexpected value: " + value);
        }
    }
}
