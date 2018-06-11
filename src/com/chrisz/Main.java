package com.chrisz;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.*;

// NOTE: Random access in Linked List is not possible so linked lists are ideal for song playlists
// Great explanation on ListIterator https://www.youtube.com/watch?v=quRRkdsY13U


public class Main {

    private static ArrayList<Album> albums = new ArrayList<Album>();

    public static void main(String[] args) {

        Album album = new Album("Ten$Ion", "Die Antwoord");
        album.addSong("I Fink U Freeky", 4.6);
        album.addSong("Baby's on Fire", 5.2);
        album.addSong("Fatty Boom Boom", 2.5);
        album.addSong("Fok Julle Naaiers", 3.5);
        album.addSong("Stormbringer", 4.5);
        albums.add(album);

        album = new Album("For those about to rock", "AC/DC");
        album.addSong("For those about to rock", 5.44);
        album.addSong("Inject the venom", 8.25);
        album.addSong("Breaking the rules", 4.3);
        album.addSong("Lets go", 4.4);
        albums.add(album);

        LinkedList<Song> playList = new LinkedList<Song>();
        // albums.get(0) will return 1st object in ArrayList albums which is
        // album by Die Antwoord
        albums.get(0).addToPlayList("Baby's on Fire", playList);
        albums.get(1).addToPlayList("For those about to rock", playList);
        albums.get(1).addToPlayList("Lets go", playList);
        albums.get(1).addToPlayList("Lets go000000", playList); // will get error
        albums.get(0).addToPlayList(3, playList);
        albums.get(0).addToPlayList(13, playList); // will get error

        play(playList);
    }

    private static void play(LinkedList<Song> playList) {
        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        boolean forward = true;
        ListIterator<Song> listIterator = playList.listIterator();
        if (playList.size() == 0) {
            System.out.println("No songs in playlist.");
            return;
        } else {
            System.out.println("Now playing " + listIterator.next().toString());
            printMenu();
        }

        while (!quit) {
            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {
                case 0:
                    System.out.println("Playlist complete.");
                    quit = true;
                    break;
                case 1: // play next song
                    if (!forward) { // if we were going backward
                        if (listIterator.hasNext()) {
                            listIterator.next(); // then move cursor to next song
                        }
                        forward = true;
                    }
                    if (listIterator.hasNext()) {
                        System.out.println("Now playing " + listIterator.next().toString());
                    } else {
                        System.out.println("We have reached the end of the playlist.");
                        forward = false;
                    }
                    break;
                case 2: // play previous song
                    if (forward) {
                        if (listIterator.hasPrevious()) {
                            listIterator.previous();
                        }
                        forward = false;
                    }
                    if (listIterator.hasPrevious()) {
                        System.out.println("Now playing " + listIterator.previous().toString());
                    } else {
                        System.out.println("This is the beginning of the playlist.");
                        forward = true;
                    }
                    break;
                case 3: // replay song
                    if (forward) {
                        if (listIterator.hasPrevious()) {
                            System.out.println("Now replaying " + listIterator.previous().toString());
                            forward = false; // we are replaying a track which is going backwards
                        } else {
                            System.out.println("This is the beginning of the playlist.");
                        }
                    } else {
                        if (listIterator.hasNext()) {
                            System.out.println("Now replaying " + listIterator.next().toString());
                            forward = true;
                        } else {
                            System.out.println("We have reached the end of the playlist.");
                        }
                    }
                    break;
                case 4:
                    printList(playList);
                    break;
                case 5:
                    printMenu();
                case 6: //remove current song from playlist
                    if (playList.size() > 0) {
                        listIterator.remove();
                        //but can't call 6 twice because listIterator is nothing, fix below
                        if(listIterator.hasNext()) {
                            System.out.println("Now playing " + listIterator.next().toString());
                        } else if(listIterator.hasPrevious()) {
                            System.out.println("Now playing " + listIterator.previous().toString());
                        }
                    } else {
                        System.out.println("Cannot delete, playlist is empty.");
                    }
                    break;

            }
        }
    }

    private static void printMenu() {
        System.out.println("Available actions:\npress");
        System.out.println("0 - to quit\n" +
                "1 - to play the next song\n" +
                "2 - to play the previous song\n" +
                "3 - to replay the current song\n" +
                "4 - list songs in the playlist\n" +
                "5 - list available actions.\n" +
                "6 - remove current song from playlist");
    }

    private static void printList(LinkedList<Song> playList) {
        // ways to iterate over Linked List
        // https://stackoverflow.com/questions/4767615/java-iterating-a-linked-list
        // enhanced for loop
        for (Song currentSong : playList) {
            System.out.println(currentSong.toString());
        }

        // iterator -> doesn't have ability to go backwards (listIterator does)
        Iterator<Song> iterator = playList.iterator();
        System.out.println("++++++++++++++++++++++");
        while (iterator.hasNext()) {
            System.out.println(iterator.next().toString());
        }
        System.out.println("++++++++++++++++++++++");
    }

}
// Create a program that implements a playlist for songs
// Create a Song class having Title and Duration for a song.
// The program will have an Album class containing a list of songs.
// The albums will be stored in an ArrayList
// Songs from different albums can be added to the playlist and will appear in the list in the order
// they are added.
// Once the songs have been added to the playlist, create a menu of options to:-
// Quit,Skip forward to the next song, skip backwards to a previous song.  Replay the current song.
// List the songs in the playlist
// A song must exist in an album before it can be added to the playlist (so you can only play songs that
// you own).
// Hint:  To replay a song, consider what happened when we went back and forth from a city before we
// started tracking the direction we were going.
// As an optional extra, provide an option to remove the current song from the playlist
// (hint: listiterator.remove()
