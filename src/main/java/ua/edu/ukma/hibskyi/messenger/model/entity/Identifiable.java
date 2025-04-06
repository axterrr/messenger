package ua.edu.ukma.hibskyi.messenger.model.entity;

public interface Identifiable<ID> {
    ID getId();

    void setId(ID id);
}
