package org.auth.model;

public class DataElement {
    private int _id;
    private String _name;

    public DataElement(int id, String name) {
        _id = id;
        _name = name;
    }

    public DataElement(DataElement other) {
        _id = other._id;
        _name = other._name;
    }

    public int getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    @Override
    public String toString() {
        return _name;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + _id;
        result = 31 * result + _name.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof DataElement)) return false;
        DataElement de = (DataElement)o;
        return _id == ((DataElement) o)._id && _name.equals(de._name);
    }
}
