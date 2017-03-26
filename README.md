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

(￣▽￣〜)

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

## Cambios talves necesarios

1. El arist devuelve albums_short, que devuelva album asi al empezar a reproducir un artist entero no tengo que hacer una request mas para pedir las canciones (igual que album con tracks seria).
Sino incluir `Track[]` en el dto de albums_short ( O `TrackShort` me da igual eso )
2. Que el endpoint para los artistas favorites del usuario no devuelva artists short (devuelva Artist), sino tengo que hacer otro pedido mas cuando lo quiera reproducir (lo core de la app tiene que ser lo mas fluido).
3. No hay endpoints para los trendings artists / songs
4. No hay endpoints para las playlists de un user
5. No hay endpoints para "seguir" o tener contactos entre usuarios
