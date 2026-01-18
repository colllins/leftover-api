package com.collins.leftover.model;

/**
 * Represents how often a user gets paid.
 * Used by PayPeriod to calculate period boundaries and expected income.
 */
public enum PayFrequency {
    WEEKLY,
    BI_WEEKLY,
    MONTHLY
}
