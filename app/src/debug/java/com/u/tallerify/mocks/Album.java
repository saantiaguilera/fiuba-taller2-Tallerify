package com.u.tallerify.mocks;

/**
 * Created by saguilera on 3/26/17.
 */

public class Album {

    /*
[
  '{{repeat(5)}}',
  {
    id: '{{index()}}',
    name: '{{firstName()}}',
    images: [
      '{{repeat(3)}}',
      "http://placehold.it/320x320"
    ],
    tracks: [
      '{{repeat(5)}}',
      {
        id: '{{index()}}',
        name: '{{firstName()}}'
      }
    ],
    artists: [
      '{{repeat(5)}}',
      {
        id: '{{index()}}',
        images: [
          '{{repeat(3)}}',
          'http://placehold.it/320x320'
        ],
        name: '{{firstName()}}',
        albums: [
          '{{repeat(2)}}',
          {
            id: '{{index()}}',
            name: '{{firstName()}}',
            images: [
              '{{repeat(3)}}',
              'http://placehold.it/320x320'
            ]
          }
        ]
      }
    ]
  }
]
     */

    public static final String RESPONSE_ALBUM = "{\n" +
        "    \"id\": 0,\n" +
        "    \"name\": \"Sherrie\",\n" +
        "    \"images\": [\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\"\n" +
        "    ],\n" +
        "    \"tracks\": [\n" +
        "      {\n" +
        "        \"id\": 0,\n" +
        "        \"name\": \"Christy\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 1,\n" +
        "        \"name\": \"Kimberley\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 2,\n" +
        "        \"name\": \"Rasmussen\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 3,\n" +
        "        \"name\": \"Palmer\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 4,\n" +
        "        \"name\": \"Lacey\"\n" +
        "      }\n" +
        "    ],\n" +
        "    \"artists\": [\n" +
        "      {\n" +
        "        \"id\": 0,\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ],\n" +
        "        \"name\": \"Gay\",\n" +
        "        \"albums\": [\n" +
        "          {\n" +
        "            \"id\": 0,\n" +
        "            \"name\": \"Natalia\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          },\n" +
        "          {\n" +
        "            \"id\": 1,\n" +
        "            \"name\": \"Garcia\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          }\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 1,\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ],\n" +
        "        \"name\": \"Kimberly\",\n" +
        "        \"albums\": [\n" +
        "          {\n" +
        "            \"id\": 0,\n" +
        "            \"name\": \"Evangeline\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          },\n" +
        "          {\n" +
        "            \"id\": 1,\n" +
        "            \"name\": \"Mueller\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          }\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 2,\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ],\n" +
        "        \"name\": \"Drake\",\n" +
        "        \"albums\": [\n" +
        "          {\n" +
        "            \"id\": 0,\n" +
        "            \"name\": \"Joseph\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          },\n" +
        "          {\n" +
        "            \"id\": 1,\n" +
        "            \"name\": \"Annie\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          }\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 3,\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ],\n" +
        "        \"name\": \"Bradshaw\",\n" +
        "        \"albums\": [\n" +
        "          {\n" +
        "            \"id\": 0,\n" +
        "            \"name\": \"Lina\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          },\n" +
        "          {\n" +
        "            \"id\": 1,\n" +
        "            \"name\": \"Simmons\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          }\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 4,\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ],\n" +
        "        \"name\": \"Carey\",\n" +
        "        \"albums\": [\n" +
        "          {\n" +
        "            \"id\": 0,\n" +
        "            \"name\": \"Sherry\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          },\n" +
        "          {\n" +
        "            \"id\": 1,\n" +
        "            \"name\": \"Burt\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          }\n" +
        "        ]\n" +
        "      }\n" +
        "    ]\n" +
        "  }";

    public static final String RESPONSE_ALBUMS = "[\n" +
        "  {\n" +
        "    \"id\": 0,\n" +
        "    \"name\": \"Sherrie\",\n" +
        "    \"images\": [\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\"\n" +
        "    ],\n" +
        "    \"tracks\": [\n" +
        "      {\n" +
        "        \"id\": 0,\n" +
        "        \"name\": \"Christy\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 1,\n" +
        "        \"name\": \"Kimberley\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 2,\n" +
        "        \"name\": \"Rasmussen\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 3,\n" +
        "        \"name\": \"Palmer\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 4,\n" +
        "        \"name\": \"Lacey\"\n" +
        "      }\n" +
        "    ],\n" +
        "    \"artists\": [\n" +
        "      {\n" +
        "        \"id\": 0,\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ],\n" +
        "        \"name\": \"Gay\",\n" +
        "        \"albums\": [\n" +
        "          {\n" +
        "            \"id\": 0,\n" +
        "            \"name\": \"Natalia\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          },\n" +
        "          {\n" +
        "            \"id\": 1,\n" +
        "            \"name\": \"Garcia\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          }\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 1,\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ],\n" +
        "        \"name\": \"Kimberly\",\n" +
        "        \"albums\": [\n" +
        "          {\n" +
        "            \"id\": 0,\n" +
        "            \"name\": \"Evangeline\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          },\n" +
        "          {\n" +
        "            \"id\": 1,\n" +
        "            \"name\": \"Mueller\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          }\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 2,\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ],\n" +
        "        \"name\": \"Drake\",\n" +
        "        \"albums\": [\n" +
        "          {\n" +
        "            \"id\": 0,\n" +
        "            \"name\": \"Joseph\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          },\n" +
        "          {\n" +
        "            \"id\": 1,\n" +
        "            \"name\": \"Annie\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          }\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 3,\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ],\n" +
        "        \"name\": \"Bradshaw\",\n" +
        "        \"albums\": [\n" +
        "          {\n" +
        "            \"id\": 0,\n" +
        "            \"name\": \"Lina\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          },\n" +
        "          {\n" +
        "            \"id\": 1,\n" +
        "            \"name\": \"Simmons\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          }\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 4,\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ],\n" +
        "        \"name\": \"Carey\",\n" +
        "        \"albums\": [\n" +
        "          {\n" +
        "            \"id\": 0,\n" +
        "            \"name\": \"Sherry\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          },\n" +
        "          {\n" +
        "            \"id\": 1,\n" +
        "            \"name\": \"Burt\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          }\n" +
        "        ]\n" +
        "      }\n" +
        "    ]\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": 1,\n" +
        "    \"name\": \"Rosetta\",\n" +
        "    \"images\": [\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\"\n" +
        "    ],\n" +
        "    \"tracks\": [\n" +
        "      {\n" +
        "        \"id\": 0,\n" +
        "        \"name\": \"Ayers\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 1,\n" +
        "        \"name\": \"Hilda\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 2,\n" +
        "        \"name\": \"Anne\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 3,\n" +
        "        \"name\": \"Pearson\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 4,\n" +
        "        \"name\": \"Thelma\"\n" +
        "      }\n" +
        "    ],\n" +
        "    \"artists\": [\n" +
        "      {\n" +
        "        \"id\": 0,\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ],\n" +
        "        \"name\": \"Lorene\",\n" +
        "        \"albums\": [\n" +
        "          {\n" +
        "            \"id\": 0,\n" +
        "            \"name\": \"Nash\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          },\n" +
        "          {\n" +
        "            \"id\": 1,\n" +
        "            \"name\": \"Valdez\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          }\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 1,\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ],\n" +
        "        \"name\": \"Ellen\",\n" +
        "        \"albums\": [\n" +
        "          {\n" +
        "            \"id\": 0,\n" +
        "            \"name\": \"Albert\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          },\n" +
        "          {\n" +
        "            \"id\": 1,\n" +
        "            \"name\": \"Karen\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          }\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 2,\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ],\n" +
        "        \"name\": \"Grimes\",\n" +
        "        \"albums\": [\n" +
        "          {\n" +
        "            \"id\": 0,\n" +
        "            \"name\": \"Montgomery\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          },\n" +
        "          {\n" +
        "            \"id\": 1,\n" +
        "            \"name\": \"Rosales\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          }\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 3,\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ],\n" +
        "        \"name\": \"Trujillo\",\n" +
        "        \"albums\": [\n" +
        "          {\n" +
        "            \"id\": 0,\n" +
        "            \"name\": \"Noreen\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          },\n" +
        "          {\n" +
        "            \"id\": 1,\n" +
        "            \"name\": \"Margaret\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          }\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 4,\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ],\n" +
        "        \"name\": \"Patty\",\n" +
        "        \"albums\": [\n" +
        "          {\n" +
        "            \"id\": 0,\n" +
        "            \"name\": \"Kirkland\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          },\n" +
        "          {\n" +
        "            \"id\": 1,\n" +
        "            \"name\": \"Priscilla\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          }\n" +
        "        ]\n" +
        "      }\n" +
        "    ]\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": 2,\n" +
        "    \"name\": \"Farley\",\n" +
        "    \"images\": [\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\"\n" +
        "    ],\n" +
        "    \"tracks\": [\n" +
        "      {\n" +
        "        \"id\": 0,\n" +
        "        \"name\": \"Gardner\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 1,\n" +
        "        \"name\": \"Alvarado\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 2,\n" +
        "        \"name\": \"Collier\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 3,\n" +
        "        \"name\": \"Cash\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 4,\n" +
        "        \"name\": \"Sonia\"\n" +
        "      }\n" +
        "    ],\n" +
        "    \"artists\": [\n" +
        "      {\n" +
        "        \"id\": 0,\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ],\n" +
        "        \"name\": \"Marci\",\n" +
        "        \"albums\": [\n" +
        "          {\n" +
        "            \"id\": 0,\n" +
        "            \"name\": \"Nichole\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          },\n" +
        "          {\n" +
        "            \"id\": 1,\n" +
        "            \"name\": \"Maxine\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          }\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 1,\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ],\n" +
        "        \"name\": \"Pamela\",\n" +
        "        \"albums\": [\n" +
        "          {\n" +
        "            \"id\": 0,\n" +
        "            \"name\": \"Williams\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          },\n" +
        "          {\n" +
        "            \"id\": 1,\n" +
        "            \"name\": \"Wilma\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          }\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 2,\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ],\n" +
        "        \"name\": \"Shaffer\",\n" +
        "        \"albums\": [\n" +
        "          {\n" +
        "            \"id\": 0,\n" +
        "            \"name\": \"Mcknight\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          },\n" +
        "          {\n" +
        "            \"id\": 1,\n" +
        "            \"name\": \"Susie\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          }\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 3,\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ],\n" +
        "        \"name\": \"Gladys\",\n" +
        "        \"albums\": [\n" +
        "          {\n" +
        "            \"id\": 0,\n" +
        "            \"name\": \"Lynn\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          },\n" +
        "          {\n" +
        "            \"id\": 1,\n" +
        "            \"name\": \"Dorothea\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          }\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 4,\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ],\n" +
        "        \"name\": \"Silva\",\n" +
        "        \"albums\": [\n" +
        "          {\n" +
        "            \"id\": 0,\n" +
        "            \"name\": \"Roxie\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          },\n" +
        "          {\n" +
        "            \"id\": 1,\n" +
        "            \"name\": \"Lopez\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          }\n" +
        "        ]\n" +
        "      }\n" +
        "    ]\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": 3,\n" +
        "    \"name\": \"Bean\",\n" +
        "    \"images\": [\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\"\n" +
        "    ],\n" +
        "    \"tracks\": [\n" +
        "      {\n" +
        "        \"id\": 0,\n" +
        "        \"name\": \"Traci\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 1,\n" +
        "        \"name\": \"Morgan\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 2,\n" +
        "        \"name\": \"Duncan\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 3,\n" +
        "        \"name\": \"Ware\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 4,\n" +
        "        \"name\": \"Marquita\"\n" +
        "      }\n" +
        "    ],\n" +
        "    \"artists\": [\n" +
        "      {\n" +
        "        \"id\": 0,\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ],\n" +
        "        \"name\": \"Lara\",\n" +
        "        \"albums\": [\n" +
        "          {\n" +
        "            \"id\": 0,\n" +
        "            \"name\": \"Summers\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          },\n" +
        "          {\n" +
        "            \"id\": 1,\n" +
        "            \"name\": \"Nadia\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          }\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 1,\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ],\n" +
        "        \"name\": \"Loraine\",\n" +
        "        \"albums\": [\n" +
        "          {\n" +
        "            \"id\": 0,\n" +
        "            \"name\": \"Boyer\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          },\n" +
        "          {\n" +
        "            \"id\": 1,\n" +
        "            \"name\": \"Christian\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          }\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 2,\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ],\n" +
        "        \"name\": \"Crystal\",\n" +
        "        \"albums\": [\n" +
        "          {\n" +
        "            \"id\": 0,\n" +
        "            \"name\": \"Salas\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          },\n" +
        "          {\n" +
        "            \"id\": 1,\n" +
        "            \"name\": \"Larson\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          }\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 3,\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ],\n" +
        "        \"name\": \"Frankie\",\n" +
        "        \"albums\": [\n" +
        "          {\n" +
        "            \"id\": 0,\n" +
        "            \"name\": \"Sonja\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          },\n" +
        "          {\n" +
        "            \"id\": 1,\n" +
        "            \"name\": \"Dominguez\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          }\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 4,\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ],\n" +
        "        \"name\": \"Robbins\",\n" +
        "        \"albums\": [\n" +
        "          {\n" +
        "            \"id\": 0,\n" +
        "            \"name\": \"Aimee\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          },\n" +
        "          {\n" +
        "            \"id\": 1,\n" +
        "            \"name\": \"Rutledge\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          }\n" +
        "        ]\n" +
        "      }\n" +
        "    ]\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": 4,\n" +
        "    \"name\": \"Michael\",\n" +
        "    \"images\": [\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\"\n" +
        "    ],\n" +
        "    \"tracks\": [\n" +
        "      {\n" +
        "        \"id\": 0,\n" +
        "        \"name\": \"Willie\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 1,\n" +
        "        \"name\": \"Addie\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 2,\n" +
        "        \"name\": \"Guzman\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 3,\n" +
        "        \"name\": \"Candy\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 4,\n" +
        "        \"name\": \"Pitts\"\n" +
        "      }\n" +
        "    ],\n" +
        "    \"artists\": [\n" +
        "      {\n" +
        "        \"id\": 0,\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ],\n" +
        "        \"name\": \"Tabatha\",\n" +
        "        \"albums\": [\n" +
        "          {\n" +
        "            \"id\": 0,\n" +
        "            \"name\": \"Warner\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          },\n" +
        "          {\n" +
        "            \"id\": 1,\n" +
        "            \"name\": \"Sally\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          }\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 1,\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ],\n" +
        "        \"name\": \"Bettie\",\n" +
        "        \"albums\": [\n" +
        "          {\n" +
        "            \"id\": 0,\n" +
        "            \"name\": \"Molina\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          },\n" +
        "          {\n" +
        "            \"id\": 1,\n" +
        "            \"name\": \"Franks\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          }\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 2,\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ],\n" +
        "        \"name\": \"Cooley\",\n" +
        "        \"albums\": [\n" +
        "          {\n" +
        "            \"id\": 0,\n" +
        "            \"name\": \"Acevedo\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          },\n" +
        "          {\n" +
        "            \"id\": 1,\n" +
        "            \"name\": \"Cross\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          }\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 3,\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ],\n" +
        "        \"name\": \"Crawford\",\n" +
        "        \"albums\": [\n" +
        "          {\n" +
        "            \"id\": 0,\n" +
        "            \"name\": \"Muriel\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          },\n" +
        "          {\n" +
        "            \"id\": 1,\n" +
        "            \"name\": \"Malinda\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          }\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 4,\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ],\n" +
        "        \"name\": \"Ofelia\",\n" +
        "        \"albums\": [\n" +
        "          {\n" +
        "            \"id\": 0,\n" +
        "            \"name\": \"Huff\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          },\n" +
        "          {\n" +
        "            \"id\": 1,\n" +
        "            \"name\": \"Arnold\",\n" +
        "            \"images\": [\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\",\n" +
        "              \"http://placehold.it/320x320\"\n" +
        "            ]\n" +
        "          }\n" +
        "        ]\n" +
        "      }\n" +
        "    ]\n" +
        "  }\n" +
        "]";

}
