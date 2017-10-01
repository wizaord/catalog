package com.orange.moos.catalog.listener;

public enum E_LISTENER {
    VALIDATION(Constants.VALIDATION),
    DECOMPOSITION(Constants.DECOMPOSITION),
    SEQUENCING(Constants.SEQUENCING);

    /**
     * Constructeur par defaut.
     *
     * @param validationValue
     */
    E_LISTENER(final String validationValue) {

    }

    /**
     * Cette classe permet d'etre utilisé dans les annotations. Il n'est pas possible de passer
     * une énumération dans les annotations. La classe {@link E_LISTENER} se base donc elle aussi sur cette
     * liste de valeurs. Les enums de la classe {@link E_LISTENER} ont donc les mêmes valeurs.
     */
    public static class Constants {
        public static final String VALIDATION = "VALIDATION";
        public static final String DECOMPOSITION = "DECOMPOSITION";
        public static final String SEQUENCING = "SEQUENCING";
    }
}
