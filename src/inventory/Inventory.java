/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory;
import javax.xml.bind.DatatypeConverter;
import com.caen.RFIDLibrary.*;
import java.util.Arrays;

/**
 *
 * @author Fabrizio
 */
public class Inventory {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CAENRFIDReader        Reader;
        CAENRFIDLogicalSource LS_0;
        CAENRFIDTag[]         tags;
        byte[]                data;

        try {
                Reader=new CAENRFIDReader();
                Reader.Connect(CAENRFIDPort.CAENRFID_RS232,"COM29");
                
                Reader.SetPower(100);
                
                LS_0=Reader.GetSource("Source_0");

                do{
                tags = LS_0.InventoryTag();
                } while (tags == null);

                for (int i = 0; i < tags.length; i++)
                {
                   System.out.println( DatatypeConverter.printHexBinary(tags[i].GetId()));
                }
                
                // data should be equal to the tag's epc code 
                data = LS_0.ReadTagData_EPC_C1G2(tags[0], (short)0x01, (short)0x04, (short)12);
                
                // change first word of the EPC code
                data[0] = (byte)(data[0] + 0x10);
                
                // Update the tag's memory
                LS_0.WriteTagData_EPC_C1G2(tags[0], (short)0x01, (short)0x04, (short)2, data);
                


            } catch (CAENRFIDException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

}
