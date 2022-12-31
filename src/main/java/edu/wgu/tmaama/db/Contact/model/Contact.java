package edu.wgu.tmaama.db.Contact.model;

/**
 * Contact model class.
 */
public class Contact {
    private int contactID;
    private String contactName;
    private String email;

    public Contact(String contactName, String email) {
        this.contactName = contactName;
        this.email = email;
    }

    public Contact(int contactID, String contactName, String email) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     * Get a contact's contactID.
     * @return
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Sets a contact's contactID.
     * @param contactID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * Get a contact's contactName.
     * @return
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Set a contact's contactName.
     * @param contactName
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Get a contact's email.
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set a contact's email.
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Overrides the toString method and returns the contact's contactName for better display in combo boxes.
     * @return
     */
    @Override
    public String toString() {
        return this.contactName;
    }
}
