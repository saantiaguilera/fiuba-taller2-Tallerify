# Tallerify

Tallerify is a "Spotify" Android client, for academic uses (its a work for the subject "Taller 2" in Facultad de Ingenieria UBA).

[![Coverage Status](https://coveralls.io/repos/github/saantiaguilera/fiuba-taller-II-tallerify-android/badge.svg?branch=develop)](https://coveralls.io/github/saantiaguilera/fiuba-taller-II-tallerify-android?branch=develop)

[![Build Status](https://travis-ci.org/saantiaguilera/fiuba-taller-II-tallerify-android.svg?branch=develop)](https://travis-ci.org/saantiaguilera/fiuba-taller-II-tallerify-android)

## Honour the JVM

How to dance correctly:

1. Swing left  : 〜(￣▽￣〜)

2. Swing right : (〜￣▽￣)〜

3. Repeat until song finishes.

## Example

〜(￣▽￣〜)

(〜￣▽￣)〜

〜(￣▽￣〜)

(〜￣▽￣)〜

〜(￣▽￣〜)

(〜￣▽￣)〜

〜(￣▽￣〜)

(〜￣▽￣)〜

〜(￣▽￣〜)

(〜￣▽￣)〜

〜(￣▽￣〜)

(〜￣▽￣)〜

〜(￣▽￣〜)

(〜￣▽￣)〜

〜(￣▽￣〜)

(〜￣▽￣)〜

〜(￣▽￣〜)

(〜￣▽￣)〜

〜(￣▽￣〜)

(〜￣▽￣)〜

〜(￣▽￣〜)

(〜￣▽￣)〜

〜(￣▽￣〜)

(〜￣▽￣)〜

〜(￣▽￣〜)

(〜￣▽￣)〜

〜(￣▽￣〜)

(〜￣▽￣)〜

〜(￣▽￣〜)

(〜￣▽￣)〜

〜(￣▽￣〜)

(〜￣▽￣)〜

〜(￣▽￣〜)

(〜￣▽￣)〜

〜(￣▽￣〜)

(〜￣▽￣)〜

〜(￣▽￣〜)

(〜￣▽￣)〜

〜(￣▽￣〜)

(〜￣▽￣)〜

〜(￣▽￣〜)

(〜￣▽￣)〜

〜(￣▽￣〜)

(〜￣▽￣)〜

〜(￣▽￣〜)

(〜￣▽￣)〜

〜(￣▽￣〜)

(〜￣▽￣)〜

## Run

Dependencies should download automatically. Please ensure you are running with the latest android and google repository versions and you have gradle 3.4 / android 2.3

For building a release apk please run `./gradlew assembleRelease -PFirebaseServiceAccountFilePath=path`

## Cambios talves necesarios

1. No hay endpoints para los trendings artists / songs (artists/trending y songs/trending ?). Los hice sin auth necesario. Un usuario que no tiene cuenta puede ver los trendings.
2. No hay endpoints para las playlists de un user (me/playlists ?)
3. No hay endpoints para "seguir" o tener contactos entre usuarios (users/{id}/follow POST/DELETE ?)
4. `AlbumShort` que conserve el campo de `images`, sino la song no tengo con que imagen mostrarla. (y no voy a hacer n*endpoints para averiguar todas)
Mismo con `ArtistShort` y con `Playlist` vamos a tener que agregar un campo `images` (que use las imagenes del album asociado a la primer cancion?). Todas por la misma razon.
5. No hay endpoints para la actividad reciente del usuario. De momento simulo que son las ultimas CANCIONES escuchadas (porque pinto)
6. UserShort necesito que tenga tambien las imagenes del wachin (de que me sirve solo el nombre...)
7. Falta endpoint de GET de songs en un album (el de artist y playlist estan tho)
8. Los endpoints de GET de songs estan mal documentados, no dice que devuelven, deberian devolver Song[]. No SongShort[].