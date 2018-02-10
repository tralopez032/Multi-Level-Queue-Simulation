// ObjectQueue.java

public class ObjectQueue implements ObjectQueueInterface{
	private Object[] item;		//objects in queue
	private int front;			//index of the object at the front of the queue
	private int rear;			//index of the object at the rear of the queue
	private int count;			//the number of elements in the queue

	/**
	 * Constructor - initializes the queue
	 */
    public ObjectQueue() {
        item = new Object[1];
        front = 0;
        rear  = -1;
        count = 0;
    }

    
    /**
     * Return whether the queue is empty or not
     * @return true if it is empty, false otherwise
     */
    public boolean isEmpty() {
        return count == 0;
    }
    
    
    /**
     * Returns whether the queue is full or not
     * @return true if the queue is full, false otherwise
     */
    public boolean isFull() {
        return count == item.length;
    }
    
    
    /**
     *Empties the queue
     */
    public void clear() {
        item = new Object[1];
        front = 0;
        rear  = -1;
        count = 0;
    }
     
    
    /**
     * Inserts a new element into the queue
     * @param o - object to insert into queue
     */
    public void insert(Object o) {
        if (isFull())
            resize(2 * item.length);
        rear = (rear+1) % item.length;
        item[rear] = o;
        ++count;
    }
    
    
    /**
     * Removes the element at the front of the queue
     * @return item at the front of queue
     */
    public Object remove() {
        if (isEmpty()) {
            System.out.println("Queue Underflow");
            System.exit(1);
        }
        Object temp = item[front];
        item[front] = null;
        front = (front+1) % item.length;
        --count;
        if (count == item.length/4 && item.length != 1)
            resize(item.length/2);
        return temp;
    }
    
    
    /**
     * Returns the elements at the front of the queue without deleting it
     * @return item at the front of queue
     */
    public Object query() {
        if (isEmpty()) {
            System.out.println("Queue Underflow");
            System.exit(1);
        }
        return item[front];
    }

    
    /**
     * Resizes the queue
     * @param size - the size to resize the queue to
     */
    private void resize(int size) {
        Object[] temp = new Object[size];
        for (int i = 0; i < count; ++i) {
            temp[i] = item[front];
            front = (front+1) % item.length;
        }
        front = 0;
        rear = count-1;
        item = temp;
    }
}


