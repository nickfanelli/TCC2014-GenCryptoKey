package br.pucc.engComp.GenCryptoKey.controller;

import java.io.*;
import java.nio.channels.*;

public class OneInstanceLock {
    private final String appName = "GenCryptoKey"; // Application name to be used on the lock file
    private File file;
    private FileChannel channel;
    private FileLock lock;

    public OneInstanceLock() {
    }

    public boolean isGenCryptoKeyAlreadyRunning() {
        try {
        	// Creates a lock file in the User's 'home' directory
            file = new File(System.getProperty("user.home"), appName + ".lock");
            channel = new RandomAccessFile(file, "rw").getChannel();

            try {
                lock = channel.tryLock();
            }
            catch (OverlappingFileLockException e) {
                // already locked
                closeLock();
                return true;
            }

            if (lock == null) {
                closeLock();
                return true;
            }

            Runtime.getRuntime().addShutdownHook(new Thread() {
                    // destroy the lock when the JVM is closing
                    public void run() {
                        closeLock();
                        deleteFile();
                    }
                });
            return false;
        }
        catch (Exception e) {
            closeLock();
            return true;
        }
    }

    private void closeLock() {
        try { 
        	lock.release();
        }
        catch (Exception e) {}
        
        try { 
        	channel.close();
        }
        catch (Exception e) {}
    }

    private void deleteFile() {
        try { 
        	file.delete();
        }
        catch (Exception e) {}
    }
}
