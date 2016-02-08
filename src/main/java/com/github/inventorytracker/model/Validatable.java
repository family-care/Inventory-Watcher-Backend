package com.github.inventorytracker.model;

import java.util.List;

/**
 * Implementing classes provide the method validate, which can be used 
 * to check if the object is valid, from a business-case point of view.
 * @author joci
 */
public interface Validatable {
    /**
     * 
     * @return list of errors as String messages
     */
    List<String> validate();
}
