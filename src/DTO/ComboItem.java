package DTO;

public class ComboItem {
	private int key;       // e.g., idDM
    private String value;  // e.g., tenDM

    public ComboItem(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value; // This is what will be shown in the JComboBox
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ComboItem other = (ComboItem) obj;
        return key == other.key;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(key);
    }
}