package com.u.tallerify.mocks;

/**
 * Created by saguilera on 3/19/17.
 */
public class User {

/*
  {
    id: '{{index()}}',
    userName: '{{firstName()}}',
    firstName: '{{firstName()}}',
    lastName: '{{surname()}}',
    images: [
      '{{repeat(3)}}',
      "http://placehold.it/320x320"
    ],
    country: '{{country()}}',
    birthday: 'null',
    email: '{{email()}}',
    contacts: [
      {
        id: '{{index()}}',
        images: [
          '{{repeat(3)}}',
          "http://placehold.it/320x320"
        ],
        userName: '{{firstName()}}'
      }
    ]
  }
*/
    public static final String RESPONSE_USER = "{\n" +
        "    \"id\": 0,\n" +
        "    \"images\": [\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\"\n" +
        "    ],\n" +
        "    \"userName\": \"Raymond Fry\",\n" +
        "    \"firstName\": \"Marian\",\n" +
        "    \"lastName\": \"Serrano\",\n" +
        "    \"country\": \"Bahrain\",\n" +
        "    \"birthday\": null,\n" +
        "    \"email\": \"marianserrano@virxo.com\",\n" +
        "    \"contacts\": [\n" +
        "      {\n" +
        "        \"id\": 0,\n" +
        "    \"images\": [\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\"\n" +
        "    ],\n" +
        "        \"userName\": \"Cathleen Dodson\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 1,\n" +
        "    \"images\": [\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\"\n" +
        "    ],\n" +
        "        \"userName\": \"Little Bauer\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 2,\n" +
        "    \"images\": [\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\"\n" +
        "    ],\n" +
        "        \"userName\": \"Ronda Graves\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 3,\n" +
        "    \"images\": [\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\"\n" +
        "    ],\n" +
        "        \"userName\": \"Lupe Olsen\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 4,\n" +
        "    \"images\": [\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\"\n" +
        "    ],\n" +
        "        \"userName\": \"Mable Clay\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 5,\n" +
        "    \"images\": [\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\"\n" +
        "    ],\n" +
        "        \"userName\": \"Casey Parrish\"\n" +
        "      }\n" +
        "    ]\n" +
        "  }";

    public static final String RESPONSE_USERS = "[\n" +
        "  {\n" +
        "    \"id\": 0,\n" +
        "    \"userName\": \"Snider\",\n" +
        "    \"firstName\": \"Savage\",\n" +
        "    \"lastName\": \"Page\",\n" +
        "    \"images\": [\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\"\n" +
        "    ],\n" +
        "    \"country\": \"Senegal\",\n" +
        "    \"birthday\": null,\n" +
        "    \"email\": \"savagepage@isotronic.com\",\n" +
        "    \"contacts\": [\n" +
        "      {\n" +
        "        \"id\": 0,\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ],\n" +
        "        \"userName\": \"Parrish\"\n" +
        "      }\n" +
        "    ]\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": 1,\n" +
        "    \"userName\": \"Coffey\",\n" +
        "    \"firstName\": \"Castaneda\",\n" +
        "    \"lastName\": \"Mendez\",\n" +
        "    \"images\": [\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\"\n" +
        "    ],\n" +
        "    \"country\": \"Pitcairn\",\n" +
        "    \"birthday\": null,\n" +
        "    \"email\": \"castanedamendez@isotronic.com\",\n" +
        "    \"contacts\": [\n" +
        "      {\n" +
        "        \"id\": 0,\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ],\n" +
        "        \"userName\": \"Hilda\"\n" +
        "      }\n" +
        "    ]\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": 2,\n" +
        "    \"userName\": \"Curry\",\n" +
        "    \"firstName\": \"Watts\",\n" +
        "    \"lastName\": \"Henson\",\n" +
        "    \"images\": [\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\"\n" +
        "    ],\n" +
        "    \"country\": \"Tuvalu\",\n" +
        "    \"birthday\": null,\n" +
        "    \"email\": \"wattshenson@isotronic.com\",\n" +
        "    \"contacts\": [\n" +
        "      {\n" +
        "        \"id\": 0,\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ],\n" +
        "        \"userName\": \"Selena\"\n" +
        "      }\n" +
        "    ]\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": 3,\n" +
        "    \"userName\": \"Dominguez\",\n" +
        "    \"firstName\": \"Autumn\",\n" +
        "    \"lastName\": \"Martinez\",\n" +
        "    \"images\": [\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\"\n" +
        "    ],\n" +
        "    \"country\": \"Togo\",\n" +
        "    \"birthday\": null,\n" +
        "    \"email\": \"autumnmartinez@isotronic.com\",\n" +
        "    \"contacts\": [\n" +
        "      {\n" +
        "        \"id\": 0,\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ],\n" +
        "        \"userName\": \"Cristina\"\n" +
        "      }\n" +
        "    ]\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": 4,\n" +
        "    \"userName\": \"Ayala\",\n" +
        "    \"firstName\": \"Dillon\",\n" +
        "    \"lastName\": \"Sellers\",\n" +
        "    \"images\": [\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\"\n" +
        "    ],\n" +
        "    \"country\": \"Czech Republic\",\n" +
        "    \"birthday\": null,\n" +
        "    \"email\": \"dillonsellers@isotronic.com\",\n" +
        "    \"contacts\": [\n" +
        "      {\n" +
        "        \"id\": 0,\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ],\n" +
        "        \"userName\": \"Meagan\"\n" +
        "      }\n" +
        "    ]\n" +
        "  }\n" +
        "]";

}
