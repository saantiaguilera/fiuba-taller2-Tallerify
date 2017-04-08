package com.u.tallerify.mocks;

/**
 * Created by saguilera on 3/19/17.
 */

public class Song {

    /*
[
  '{{repeat(5)}}',
  {
    id: '{{index()}}',
    name: '{{firstName()}}',
    duration: '{{integer(30, 600)}}',
    href: 'http://some_href.com/href',
    artists: [
      '{{repeat(5)}}',
      {
        id: '{{index()}}',
        name: '{{firstName()}}',
        images: [
          '{{repeat(3)}}',
          'https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg'
        ]
      }
    ],
    album: {
      id: '{{index()}}',
      name: '{{firstName()}}',
      images: [
        '{{repeat(3)}}',
        'https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg'
      ]
    }
  }
]
     */

    public static final String RESPONSE_SONG = "{\n" +
        "    \"id\": 0,\n" +
        "    \"name\": \"Berger\",\n" +
        "    \"duration\": 171,\n" +
        "    \"href\": \"http://some_href.com/href\",\n" +
        "    \"artists\": [\n" +
        "      {\n" +
        "        \"id\": 0,\n" +
        "        \"name\": \"Cummings\",\n" +
        "        \"images\": [\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\"\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 1,\n" +
        "        \"name\": \"Carmen\",\n" +
        "        \"images\": [\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\"\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 2,\n" +
        "        \"name\": \"Estrada\",\n" +
        "        \"images\": [\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\"\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 3,\n" +
        "        \"name\": \"Lowery\",\n" +
        "        \"images\": [\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\"\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 4,\n" +
        "        \"name\": \"Carr\",\n" +
        "        \"images\": [\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\"\n" +
        "        ]\n" +
        "      }\n" +
        "    ],\n" +
        "    \"album\": {\n" +
        "      \"id\": 0,\n" +
        "      \"name\": \"Cortez\",\n" +
        "      \"images\": [\n" +
        "        \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "        \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "        \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\"\n" +
        "      ]\n" +
        "    }\n" +
        "  }";

    public static final String RESPONSE_TRENDING_SONGS = "[\n" +
        "  {\n" +
        "    \"id\": 0,\n" +
        "    \"name\": \"Berger\",\n" +
        "    \"duration\": 171,\n" +
        "    \"href\": \"http://some_href.com/href\",\n" +
        "    \"artists\": [\n" +
        "      {\n" +
        "        \"id\": 0,\n" +
        "        \"name\": \"Cummings\",\n" +
        "        \"images\": [\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\"\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 1,\n" +
        "        \"name\": \"Carmen\",\n" +
        "        \"images\": [\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\"\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 2,\n" +
        "        \"name\": \"Estrada\",\n" +
        "        \"images\": [\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\"\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 3,\n" +
        "        \"name\": \"Lowery\",\n" +
        "        \"images\": [\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\"\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 4,\n" +
        "        \"name\": \"Carr\",\n" +
        "        \"images\": [\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\"\n" +
        "        ]\n" +
        "      }\n" +
        "    ],\n" +
        "    \"album\": {\n" +
        "      \"id\": 0,\n" +
        "      \"name\": \"Cortez\",\n" +
        "      \"images\": [\n" +
        "        \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "        \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "        \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\"\n" +
        "      ]\n" +
        "    }\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": 1,\n" +
        "    \"name\": \"Snow\",\n" +
        "    \"duration\": 490,\n" +
        "    \"href\": \"http://some_href.com/href\",\n" +
        "    \"artists\": [\n" +
        "      {\n" +
        "        \"id\": 0,\n" +
        "        \"name\": \"Pugh\",\n" +
        "        \"images\": [\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\"\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 1,\n" +
        "        \"name\": \"Prince\",\n" +
        "        \"images\": [\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\"\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 2,\n" +
        "        \"name\": \"Sybil\",\n" +
        "        \"images\": [\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\"\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 3,\n" +
        "        \"name\": \"Rebecca\",\n" +
        "        \"images\": [\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\"\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 4,\n" +
        "        \"name\": \"Castro\",\n" +
        "        \"images\": [\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\"\n" +
        "        ]\n" +
        "      }\n" +
        "    ],\n" +
        "    \"album\": {\n" +
        "      \"id\": 1,\n" +
        "      \"name\": \"Vaughan\",\n" +
        "      \"images\": [\n" +
        "        \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "        \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "        \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\"\n" +
        "      ]\n" +
        "    }\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": 2,\n" +
        "    \"name\": \"Vanessa\",\n" +
        "    \"duration\": 431,\n" +
        "    \"href\": \"http://some_href.com/href\",\n" +
        "    \"artists\": [\n" +
        "      {\n" +
        "        \"id\": 0,\n" +
        "        \"name\": \"Darcy\",\n" +
        "        \"images\": [\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\"\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 1,\n" +
        "        \"name\": \"Ramsey\",\n" +
        "        \"images\": [\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\"\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 2,\n" +
        "        \"name\": \"Delgado\",\n" +
        "        \"images\": [\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\"\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 3,\n" +
        "        \"name\": \"Ruthie\",\n" +
        "        \"images\": [\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\"\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 4,\n" +
        "        \"name\": \"Kay\",\n" +
        "        \"images\": [\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\"\n" +
        "        ]\n" +
        "      }\n" +
        "    ],\n" +
        "    \"album\": {\n" +
        "      \"id\": 2,\n" +
        "      \"name\": \"Savage\",\n" +
        "      \"images\": [\n" +
        "        \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "        \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "        \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\"\n" +
        "      ]\n" +
        "    }\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": 3,\n" +
        "    \"name\": \"Bryant\",\n" +
        "    \"duration\": 419,\n" +
        "    \"href\": \"http://some_href.com/href\",\n" +
        "    \"artists\": [\n" +
        "      {\n" +
        "        \"id\": 0,\n" +
        "        \"name\": \"Luisa\",\n" +
        "        \"images\": [\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\"\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 1,\n" +
        "        \"name\": \"Alisha\",\n" +
        "        \"images\": [\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\"\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 2,\n" +
        "        \"name\": \"Rios\",\n" +
        "        \"images\": [\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\"\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 3,\n" +
        "        \"name\": \"Summer\",\n" +
        "        \"images\": [\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\"\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 4,\n" +
        "        \"name\": \"Cecelia\",\n" +
        "        \"images\": [\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\"\n" +
        "        ]\n" +
        "      }\n" +
        "    ],\n" +
        "    \"album\": {\n" +
        "      \"id\": 3,\n" +
        "      \"name\": \"Lenore\",\n" +
        "      \"images\": [\n" +
        "        \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "        \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "        \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\"\n" +
        "      ]\n" +
        "    }\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": 4,\n" +
        "    \"name\": \"Amie\",\n" +
        "    \"duration\": 542,\n" +
        "    \"href\": \"http://some_href.com/href\",\n" +
        "    \"artists\": [\n" +
        "      {\n" +
        "        \"id\": 0,\n" +
        "        \"name\": \"Milagros\",\n" +
        "        \"images\": [\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\"\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 1,\n" +
        "        \"name\": \"Callahan\",\n" +
        "        \"images\": [\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\"\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 2,\n" +
        "        \"name\": \"Fox\",\n" +
        "        \"images\": [\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\"\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 3,\n" +
        "        \"name\": \"Jaclyn\",\n" +
        "        \"images\": [\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\"\n" +
        "        ]\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 4,\n" +
        "        \"name\": \"Dana\",\n" +
        "        \"images\": [\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "          \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\"\n" +
        "        ]\n" +
        "      }\n" +
        "    ],\n" +
        "    \"album\": {\n" +
        "      \"id\": 4,\n" +
        "      \"name\": \"Roslyn\",\n" +
        "      \"images\": [\n" +
        "        \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "        \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\",\n" +
        "        \"https://upload.wikimedia.org/wikipedia/en/b/bb/Hate_Crew_Deathroll_%28musical_album%29.jpg\"\n" +
        "      ]\n" +
        "    }\n" +
        "  }\n" +
        "]";

    public static final String RESPONSE_RESOLVED_URI = "{" +
        "\"songId\": 1,\n" +
        "\"url\": \"http://k004.kiwi6.com/hotlink/e69wkffb5o/Strange_Life.mp3\"" +
        "}";


}
