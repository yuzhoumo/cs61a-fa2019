package gitlet;

import java.io.Serializable;

/** An interface describing Gitlet objects.
 *  @author Joe Mo */
interface GitletObject extends Serializable {

    /** Return type of object (either commit or blob). */
    String objectType();

    /** Return the SHA-1 hash of the object. */
    String hash();

}
