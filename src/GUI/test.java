package GUI;


import BUS.HoaDonBUS;
import DTO.CTHoaDonDTO;

import java.util.ArrayList;
import java.util.List;

public class test {
    public static void main(String[] args) throws Exception {
        List<CTHoaDonDTO> ctHoaDonList= new ArrayList<>();
        ctHoaDonList.add(new CTHoaDonDTO(1, 1, 2, 30000)); // Cà phê sữa
        ctHoaDonList.add(new CTHoaDonDTO(2, 1, 1, 35000)); // Trà đào

        HoaDonBUS hoaDonBUS = new HoaDonBUS();
        hoaDonBUS.printHoaDon(ctHoaDonList);
    }
}
