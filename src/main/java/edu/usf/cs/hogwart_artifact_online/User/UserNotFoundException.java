package edu.usf.cs.hogwart_artifact_online.User;

/**

 * How it works with super():
 * 1. UserNotFoundException is the CHILD class
 * 2. RuntimeException is the PARENT class
 * 3. super() calls the parent constructor and passes the message
 * 4. Parent stores the message in its private field
 * 5. Child inherits access to getMessage() method from parent
 *
 * When exception is thrown and caught:
 *   throw new UserNotFoundException(999)
 *   → Creates exception with message stored in parent class
 *
 *   ExceptionHandler catches it:
 *   exception.getMessage()
 *   → Returns: "Could not find artifact with Id 999"
 *   → Message comes from parent's field via inherited method
 */
public class UserNotFoundException extends RuntimeException {


    public UserNotFoundException(Integer id) {
        super("Could not find artifact with Id " + id);
        // super() = Tell parent RuntimeException to store this message
        // This message is inherited by child(UserNot..) and used later by exception handler
    }
}

/**
 * REFERENCE: What RuntimeException (parent class) looks like:
 *
 * public class RuntimeException extends Exception {
 *     private String message;  ← Stores the error message here
 *                              ← PRIVATE = Child CANNOT directly access!
 *
 *     public RuntimeException(String msg) {
 *         this.message = msg;  ← When super() is called, message gets stored
 *     }
 *
 *     public String getMessage() {
 *         return this.message;  ← PUBLIC METHOD = Child CAN use this!
 *     }
 * }
 *
 * IMPORTANT: Encapsulation Rule
 * ────────────────────────────────────
 * Child class INHERITS the message field from parent
 *   ↓ BUT
 * message is PRIVATE, so child CANNOT directly access it
 *   ↓ SO
 * Child uses public getMessage() method instead
 *   ↓
 * exception.getMessage()  ← This works!
 * exception.message       ← This would give COMPILE ERROR (private field)
 *
 * Flow:
 *   super("Could not find artifact with Id 999")
 *   ↓
 *   RuntimeException stores: message = "Could not find artifact with Id 999"
 *   ↓
 *   UserNotFoundException object now carries that message (inherited)
 *   ↓
 *   Handler retrieves it: exception.getMessage() (via public method)
 */

