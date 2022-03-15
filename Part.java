package example.zxing;

public class Part {

            public String kind;  // 사용자 id
            public String code; // 사용자명
            public String barcode; // 계정명
            public String name; // 개인키
            public String std; // 비밀키
            public String qtybox;
            public String box;
            public String qty;// 메모 및 주석

            // TODO : get,set 함수 생략
            public String getKind() {
                return kind;
            }

            public void setKind(String kind) {
                this.kind = kind;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getBarcode() {
                return barcode;
            }

            public void setBarcode(String barcode) {
                this.barcode = barcode;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getStd() {
                return std;
            }

            public void setStd(String std) {
                this.std = std;
            }

            public String getQtybox() {
                return qtybox;
            }

            public void setQtybox(String qtybox) {
                this.qtybox = qtybox;
            }

            public String getBox() {
                return box;
            }

            public void setBox(String box) {
                this.box = box;
            }

            public String getQty() {
                return qty;
            }

            public void setQty(String qty) {
                this.qty = qty;
            }

        }

