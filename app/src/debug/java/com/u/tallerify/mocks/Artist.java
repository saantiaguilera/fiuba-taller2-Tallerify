package com.u.tallerify.mocks;

/**
 * Created by saguilera on 3/19/17.
 */

public class Artist {

    /*
[
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
     */

    public static final String RESPONSE_ARTIST = "{\n" +
        "    \"id\": 0,\n" +
        "    \"images\": [\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\"\n" +
        "    ],\n" +
        "    \"name\": \"Henry\",\n" +
        "    \"albums\": [\n" +
        "      {\n" +
        "        \"id\": 0,\n" +
        "        \"name\": \"Dianna\",\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 1,\n" +
        "        \"name\": \"Paul\",\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ]\n" +
        "      }\n" +
        "    ]\n" +
        "  }";

    public static final String RESPONSE_ARTISTS_TRENDING = "[\n" +
        "  {\n" +
        "    \"id\": 0,\n" +
        "    \"images\": [\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\"\n" +
        "    ],\n" +
        "    \"name\": \"Henry\",\n" +
        "    \"albums\": [\n" +
        "      {\n" +
        "        \"id\": 0,\n" +
        "        \"name\": \"Dianna\",\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 1,\n" +
        "        \"name\": \"Paul\",\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ]\n" +
        "      }\n" +
        "    ]\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": 1,\n" +
        "    \"images\": [\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\"\n" +
        "    ],\n" +
        "    \"name\": \"Lynn\",\n" +
        "    \"albums\": [\n" +
        "      {\n" +
        "        \"id\": 0,\n" +
        "        \"name\": \"Jerri\",\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 1,\n" +
        "        \"name\": \"Elva\",\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ]\n" +
        "      }\n" +
        "    ]\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": 2,\n" +
        "    \"images\": [\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\"\n" +
        "    ],\n" +
        "    \"name\": \"Evelyn\",\n" +
        "    \"albums\": [\n" +
        "      {\n" +
        "        \"id\": 0,\n" +
        "        \"name\": \"Vance\",\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 1,\n" +
        "        \"name\": \"Silva\",\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ]\n" +
        "      }\n" +
        "    ]\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": 3,\n" +
        "    \"images\": [\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\"\n" +
        "    ],\n" +
        "    \"name\": \"Jan\",\n" +
        "    \"albums\": [\n" +
        "      {\n" +
        "        \"id\": 0,\n" +
        "        \"name\": \"Moran\",\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 1,\n" +
        "        \"name\": \"Pickett\",\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ]\n" +
        "      }\n" +
        "    ]\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": 4,\n" +
        "    \"images\": [\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\"\n" +
        "    ],\n" +
        "    \"name\": \"Rochelle\",\n" +
        "    \"albums\": [\n" +
        "      {\n" +
        "        \"id\": 0,\n" +
        "        \"name\": \"Chrystal\",\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 1,\n" +
        "        \"name\": \"Katy\",\n" +
        "        \"images\": [\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\",\n" +
        "          \"http://placehold.it/320x320\"\n" +
        "        ]\n" +
        "      }\n" +
        "    ]\n" +
        "  }\n" +
        "]";

}
