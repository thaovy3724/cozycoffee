package BUS;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import DAO.HoaDonDAO;
import DAO.Lo_NguyenLieuDAO;
import DAO.NguyenLieuDAO;
import DAO.PhieuNhapDAO;
import DTO.Lo_NguyenLieuDTO;
import DTO.NguyenLieuDTO;
import DTO.PhieuNhapDTO;

/**
 * @author: huonglamcoder
 */

// HUONGNGUYEN 3/5
public class ThongKeBUS {
    HoaDonDAO hoaDonDAO = new HoaDonDAO();
    PhieuNhapDAO phieuNhapDAO = new PhieuNhapDAO();
    NguyenLieuDAO nguyenLieuDAO = new NguyenLieuDAO();
    Lo_NguyenLieuDAO lo_nguyenLieuDAO = new Lo_NguyenLieuDAO();


    // TrongHiuuu 4/5/2025
    public List<List<Long>> getProfitStatisticByYear(int year) {
        List<Long> doanhThu = hoaDonDAO.getTongTienByYear(year);
        List<Long> tongChi = phieuNhapDAO.getTongTienEachInvoiceByYear(year);
        List<Long> loiNhuan = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
           loiNhuan.add(doanhThu.get(i) - tongChi.get(i));
        }

        return List.of(tongChi, doanhThu, loiNhuan);
    }

    public List<List<Long>> getProfitStatisticByMonth(int month, int year) {
        List<Long> doanhThuByDay = hoaDonDAO.getTongTienByMonth(month, year);
        List<Long> tongChiByDay = phieuNhapDAO.getTongTienEachInvoiceByMonth(month, year);
        List<Long> loiNhuanByDay = new ArrayList<>();

        List<Long> doanhThu = new ArrayList<>();
        List<Long> tongChi = new ArrayList<>();
        List<Long> loiNhuan = new ArrayList<>();

        int monthSize = doanhThuByDay.size();

        Long sumTongChi = 0L;
        Long sumDoanhThu = 0L;
        Long sumLoiNhuan = 0L;
        int daysInWeek = 7;

        for (int i = 0; i < monthSize; i++) {
            int index = i + 1; // Ngày (1-based)
            sumTongChi += tongChiByDay.get(i);
            sumDoanhThu += doanhThuByDay.get(i);
            sumLoiNhuan += doanhThuByDay.get(i) - tongChiByDay.get(i);

            // Nếu là ngày cuối của nhóm 7 ngày hoặc ngày cuối của tháng
            if (index % daysInWeek == 0 || i == monthSize - 1) {
                doanhThu.add(sumDoanhThu);
                tongChi.add(sumTongChi);
                loiNhuan.add(sumLoiNhuan);

                sumTongChi = 0L;
                sumDoanhThu = 0L;
                sumLoiNhuan = 0L;
            }
        }

        return List.of(tongChi, doanhThu, loiNhuan);
    }
// List<Map<tennl, List<Tongtien>>>
// => List<Map<String,List<Long>>>
    public List<Map<String, List<Long>>> getIngredientsCostStatisticByYear(int year) {
        return phieuNhapDAO.getIngredientsCostStatisticByYear(year);
    }

    public List<Map<String, List<Long>>> getIngredientsCostStatisticByMonth(int month, int year) {
        // Định dạng 28/29/30/31 ngày
        List<Map<String, List<Long>>> weeklyResult = new ArrayList<>();
        List<Map<String, List<Long>>> monthlyResult = phieuNhapDAO.getIngredientsCostStatisticByMonth(month, year);

        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());
        List<LocalDate[]> weekRanges = getWeekRanges(startOfMonth, endOfMonth);

        for (Map<String, List<Long>> ingredientMap : monthlyResult) { // Với mỗi Map có trong List Map
            for (Map.Entry<String, List<Long>> entry : ingredientMap.entrySet()) { // Với mỗi map, duyệt từng entry (cặp key - value)
                String ingredientName = entry.getKey();
                List<Long> dailyCosts = entry.getValue();

                List<Long> weeklyCosts = new ArrayList<>();
                for (int i = 0; i < weekRanges.size(); i++) {
                    int start = weekRanges.get(i)[0].getDayOfMonth();
                    int end = weekRanges.get(i)[1].getDayOfMonth();

                    long sum = 0;
                    for (int j = start; j <= end; j++) {
                        if (j < dailyCosts.size()) {
                            sum += dailyCosts.get(j);
                        }
                    }
                    weeklyCosts.add(sum);
                }
                Map<String, List<Long>> weeklyMap = new LinkedHashMap<>();
                weeklyMap.put(ingredientName, weeklyCosts);
                weeklyResult.add(weeklyMap);
            }
        }
        return weeklyResult;
    }

    public List<Lo_NguyenLieuDTO> getChoseIngredientBatchList(int idNL, LocalDate start, LocalDate end) {
        NguyenLieuDTO nl = nguyenLieuDAO.findByIdNL(idNL);
        if (nl == null) {
            return new ArrayList<>();
        }
        List<PhieuNhapDTO> danhSachPN = phieuNhapDAO.searchCompleteByDate(java.sql.Date.valueOf(start), Date.valueOf(end));
        List<Lo_NguyenLieuDTO> ingredientBatchList = new ArrayList<>();

        for (PhieuNhapDTO pn : danhSachPN) {
            List<Lo_NguyenLieuDTO> loList = lo_nguyenLieuDAO.getAllByIdPN(pn.getIdPN());
            for (Lo_NguyenLieuDTO lo : loList) {
                if (lo.getIdNL() == nl.getIdNL()) {
                    ingredientBatchList.add(lo);
                }
            }
        }
        return ingredientBatchList;
    }

    public List<Map<String, List<Long>>> getProductsQuantityStatisticByYear(int year) {
        return hoaDonDAO.getProductsQuantityStatisticByYear(year);
    }

    public List<Map<String, List<Long>>> getProductsQuantityStatisticByMonth(int month, int year) {
        // Định dạng 28/29/30/31 ngày
        List<Map<String, List<Long>>> weeklyResult = new ArrayList<>();
        List<Map<String, List<Long>>> monthlyResult = hoaDonDAO.getProductsQuantityStatisticByMonth(month, year);

        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());
        List<LocalDate[]> weekRanges = getWeekRanges(startOfMonth, endOfMonth);

        for (Map<String, List<Long>> productMap : monthlyResult) { // Với mỗi Map có trong List Map
            for (Map.Entry<String, List<Long>> entry : productMap.entrySet()) { // Với mỗi map, duyệt từng entry (cặp key - value)
                String productName = entry.getKey();
                List<Long> dailyCosts = entry.getValue();

                List<Long> weeklyCosts = new ArrayList<>();
                for (int i = 0; i < weekRanges.size(); i++) {
                    int start = weekRanges.get(i)[0].getDayOfMonth();
                    int end = weekRanges.get(i)[1].getDayOfMonth();

                    long sum = 0;
                    for (int j = start; j <= end; j++) {
                        if (j < dailyCosts.size()) {
                            sum += dailyCosts.get(j);
                        }
                    }
                    weeklyCosts.add(sum);
                }
                Map<String, List<Long>> weeklyMap = new LinkedHashMap<>();
                weeklyMap.put(productName, weeklyCosts);
                weeklyResult.add(weeklyMap);
            }
        }
        return weeklyResult;
    }

    private List<LocalDate[]> getWeekRanges(LocalDate startOfMonth, LocalDate endOfMonth) {
        List<LocalDate[]> weekRanges = new ArrayList<>();
        LocalDate[][] tempWeekRanges = new LocalDate[5][2];
        tempWeekRanges[0] = new LocalDate[]{startOfMonth, startOfMonth.plusDays(6)};
        tempWeekRanges[1] = new LocalDate[]{startOfMonth.plusDays(7), startOfMonth.plusDays(13)};
        tempWeekRanges[2] = new LocalDate[]{startOfMonth.plusDays(14), startOfMonth.plusDays(20)};
        tempWeekRanges[3] = new LocalDate[]{startOfMonth.plusDays(21), startOfMonth.plusDays(27)};
        tempWeekRanges[4] = new LocalDate[]{startOfMonth.plusDays(28), endOfMonth};

        for (LocalDate[] week : tempWeekRanges) {
            LocalDate from = week[0];
            LocalDate to = week[1].isAfter(endOfMonth) ? endOfMonth : week[1];
            if (!from.isAfter(endOfMonth)) {
                weekRanges.add(new LocalDate[]{from, to});
            }
        }
        return weekRanges;
    }
}
