package com.u.tallerify.mocks;

import static com.u.tallerify.mocks.Song.RESPONSE_SONG;
import static com.u.tallerify.mocks.User.RESPONSE_USER;

/**
 * Created by saguilera on 3/19/17.
 */
public class Playlist {

    /*
[
  '{{repeat(5)}}',
  {
    id: '{{index()}}',
    name: '{{firstName()}}',
    description: '{{lorem(15, "words")}}',
    href: 'http://some_href.com/href',
    owner: {
      id: '{{index()}}',
      userName: '{{firstName()}}'
    },
    images: [
      '{{repeat(3)}}',
      "http://placehold.it/320x320"
    ],
    tracks: [
      '{{repeat(5)}}',
      {
        id: '{{index()}}',
        name: '{{firstName()}}',
      }
    ]
  }
]
     */

    public static final String RESPONSE_USER_PLAYLIST = "{\n" +
        "    \"id\": 0,\n" +
        "    \"name\": \"Houston\",\n" +
        "    \"description\": \"est non cillum incididunt veniam duis aute sunt dolor mollit aliqua id duis et ex\",\n" +
        "    \"href\": \"http://some_href.com/href\",\n" +
        "    \"owner\": {\n" +
        "      \"id\": 0,\n" +
        "      \"userName\": \"Pearl\"\n" +
        "    },\n" +
        "    \"images\": [\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\"\n" +
        "    ],\n" +
        "    \"tracks\": [\n" +
        "      {\n" +
        "        \"id\": 0,\n" +
        "        \"name\": \"Maxwell\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 1,\n" +
        "        \"name\": \"Mclaughlin\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 2,\n" +
        "        \"name\": \"Earlene\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 3,\n" +
        "        \"name\": \"Candy\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 4,\n" +
        "        \"name\": \"Turner\"\n" +
        "      }\n" +
        "    ]\n" +
        "  }";

    public static final String RESPONSE_USER_PLAYLISTS = "[\n" +
        "  {\n" +
        "    \"id\": 0,\n" +
        "    \"name\": \"Houston\",\n" +
        "    \"description\": \"est non cillum incididunt veniam duis aute sunt dolor mollit aliqua id duis et ex\",\n" +
        "    \"href\": \"http://some_href.com/href\",\n" +
        "    \"owner\": {\n" +
        "      \"id\": 0,\n" +
        "      \"userName\": \"Pearl\"\n" +
        "    },\n" +
        "    \"images\": [\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\"\n" +
        "    ],\n" +
        "    \"tracks\": [\n" +
        "      {\n" +
        "        \"id\": 0,\n" +
        "        \"name\": \"Maxwell\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 1,\n" +
        "        \"name\": \"Mclaughlin\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 2,\n" +
        "        \"name\": \"Earlene\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 3,\n" +
        "        \"name\": \"Candy\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 4,\n" +
        "        \"name\": \"Turner\"\n" +
        "      }\n" +
        "    ]\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": 1,\n" +
        "    \"name\": \"Salazar\",\n" +
        "    \"description\": \"id sit quis anim ipsum labore aliqua veniam sunt nulla duis sit esse et cillum\",\n" +
        "    \"href\": \"http://some_href.com/href\",\n" +
        "    \"owner\": {\n" +
        "      \"id\": 1,\n" +
        "      \"userName\": \"Scott\"\n" +
        "    },\n" +
        "    \"images\": [\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\"\n" +
        "    ],\n" +
        "    \"tracks\": [\n" +
        "      {\n" +
        "        \"id\": 0,\n" +
        "        \"name\": \"Gertrude\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 1,\n" +
        "        \"name\": \"Charles\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 2,\n" +
        "        \"name\": \"Anthony\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 3,\n" +
        "        \"name\": \"Carolyn\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 4,\n" +
        "        \"name\": \"Shelby\"\n" +
        "      }\n" +
        "    ]\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": 2,\n" +
        "    \"name\": \"Warner\",\n" +
        "    \"description\": \"ea nostrud et non aliqua reprehenderit est aute incididunt eiusmod consectetur sit veniam sunt cupidatat\",\n" +
        "    \"href\": \"http://some_href.com/href\",\n" +
        "    \"owner\": {\n" +
        "      \"id\": 2,\n" +
        "      \"userName\": \"Meagan\"\n" +
        "    },\n" +
        "    \"images\": [\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\"\n" +
        "    ],\n" +
        "    \"tracks\": [\n" +
        "      {\n" +
        "        \"id\": 0,\n" +
        "        \"name\": \"Madeleine\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 1,\n" +
        "        \"name\": \"Elliott\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 2,\n" +
        "        \"name\": \"Gilmore\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 3,\n" +
        "        \"name\": \"Lizzie\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 4,\n" +
        "        \"name\": \"Merrill\"\n" +
        "      }\n" +
        "    ]\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": 3,\n" +
        "    \"name\": \"Consuelo\",\n" +
        "    \"description\": \"aliqua ex nostrud non elit ut culpa elit nulla ullamco eiusmod sit aliqua velit veniam\",\n" +
        "    \"href\": \"http://some_href.com/href\",\n" +
        "    \"owner\": {\n" +
        "      \"id\": 3,\n" +
        "      \"userName\": \"Carney\"\n" +
        "    },\n" +
        "    \"images\": [\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\"\n" +
        "    ],\n" +
        "    \"tracks\": [\n" +
        "      {\n" +
        "        \"id\": 0,\n" +
        "        \"name\": \"Pierce\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 1,\n" +
        "        \"name\": \"Wanda\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 2,\n" +
        "        \"name\": \"Weber\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 3,\n" +
        "        \"name\": \"Leona\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 4,\n" +
        "        \"name\": \"Henrietta\"\n" +
        "      }\n" +
        "    ]\n" +
        "  },\n" +
        "  {\n" +
        "    \"id\": 4,\n" +
        "    \"name\": \"Jenna\",\n" +
        "    \"description\": \"nostrud ut eiusmod commodo elit eiusmod sint elit occaecat non Lorem elit fugiat laboris sunt\",\n" +
        "    \"href\": \"http://some_href.com/href\",\n" +
        "    \"owner\": {\n" +
        "      \"id\": 4,\n" +
        "      \"userName\": \"Georgina\"\n" +
        "    },\n" +
        "    \"images\": [\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\",\n" +
        "      \"http://placehold.it/320x320\"\n" +
        "    ],\n" +
        "    \"tracks\": [\n" +
        "      {\n" +
        "        \"id\": 0,\n" +
        "        \"name\": \"Wall\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 1,\n" +
        "        \"name\": \"Paul\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 2,\n" +
        "        \"name\": \"Beverly\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 3,\n" +
        "        \"name\": \"Wolf\"\n" +
        "      },\n" +
        "      {\n" +
        "        \"id\": 4,\n" +
        "        \"name\": \"Hardy\"\n" +
        "      }\n" +
        "    ]\n" +
        "  }\n" +
        "]";

}
