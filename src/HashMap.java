/**
 * @author ekaterina
 * TODO automatic resize
 */
public class HashMap<K, V> implements HashMapInterface{
    private Entry<K, V>[] entries;

    private Entry<K, V> DEFUNCT = new Entry<K, V>();

    private int capacity = 1001;//FIXME
    private int size = 0;

    public HashMap(){
        entries = (Entry<K, V>[]) new Entry[capacity];
    }

    public void put(K key, V value){
        int index = getCompressed(getHash(key));
        Entry entry = new Entry(key, value);
        if((entries[index] == null)||(entries[index].equals(DEFUNCT))){
            entries[index] = entry;
        }else{
            index = getAnotherIndex(index);
            entries[index] = entry;
        }
        size++;
    }

    private long getHash(K key){
        return key.hashCode();
    }

    private int getCompressed(long hash){
        return (int) Math.abs(hash%capacity);
    }

    private int getAnotherIndex(int index) {
        int k = 1;
        while((entries[index + k] != null)&&(!entries[index + k].equals(DEFUNCT))){
            k *= 2;
        }
        return index + k;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    //TODO
    public V remove(K key) {
        int index = getCompressed(getHash(key));
        if((entries[index] == null)||(entries[index].equals(DEFUNCT)))
            return null;

        if(!entries[index].getKey().equals(key)){
            index = findKey(key);
        }
        if(index == -1)
            return null;
        V value = entries[index].getValue();
        entries[index].setValue(null);
        entries[index].setKey(null);
        entries[index] = DEFUNCT;
        size--;
        return value;
    }

    public V get(K key){
        int index = getCompressed(getHash(key));

        if(entries[index] == null) return null;

        if((entries[index] != null)&&(!entries[index].equals(DEFUNCT))&&(entries[index].getKey().equals(key))){
            return entries[index].getValue();
        }else{
            index = findKey(key);
            if(index == -1)//If not found
                return null;
            else
                return entries[index].getValue();
        }
    }

    private int findKey(K key) {
        int index = getCompressed(getHash(key));
        int k = 1;
        boolean isFound = false;

        while(entries[getCompressed(index + k)] != null){
            if ((entries[getCompressed(index + k)] != null)&&(entries[getCompressed(index + k)].getKey().equals(key))) {
                isFound = true;
                break;
            }
            k *= 2;
        }

        if(!isFound)
            return -1;

        return getCompressed(index + k);
    }

    public Object[] getValues(){
        Object[] a = new Object[size];
        int index = 0;

        for (int i = 0; i < capacity; i++) {
            if(entries[i] != null) {
                a[index] = entries[i].getValue();
                index++;
            }
        }

        return a;
    }

    public Object[] getKeys(){
        Object[] a = new Object[size];
        int index = 0;

        for (int i = 0; i < capacity; i++) {
            if((entries[i] != null)&&(!entries[i].equals(DEFUNCT))){
                a[index] = entries[i].getKey();
                index++;
            }

        }
        return a;
    }

}
