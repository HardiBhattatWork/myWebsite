import java.io.*;

//******************************************************
class SerializedObject implements Serializable
{
   private byte array[] = null;

   public SerializedObject() {
   }

   public void setArray(byte array[]) {
     this.array = array;
   }

   public byte[] getArray() {
     return array;
   }
}

