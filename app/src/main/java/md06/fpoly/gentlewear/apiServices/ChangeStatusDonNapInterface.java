package md06.fpoly.gentlewear.apiServices;

import md06.fpoly.gentlewear.models.NapTien;
import md06.fpoly.gentlewear.models.ThanhToan;


public interface ChangeStatusDonNapInterface {
    void onChange(String id);
    void openDetail(ThanhToan item);
    void openDetailMoney(NapTien item);
}
