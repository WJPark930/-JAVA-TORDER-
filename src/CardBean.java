public class CardBean {
    private String cardid;
    private String userid;
    private String cardtype;
    private String expiryDate; // 변경 전: expiryda, 변경 후: expiryDate
    private String cvc;
	public String getCardid() {
		return cardid;
	}
	public String getUserid() {
		return userid;
	}
	public String getCardtype() {
		return cardtype;
	}
	public String getExpirydate() {
		return expiryDate;
	}
	public String getCvc() {
		return cvc;
	}
	public void setCardid(String cardid) {
		this.cardid = cardid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}
	public void setExpirydate(String expirydate) {
		this.expiryDate = expirydate;
	}
	public void setCvc(String cvc) {
		this.cvc = cvc;
	}
	public CardBean(String cardid, String userid, String cardtype, String expirydate, String cvc) {
		super();
		this.cardid = cardid;
		this.userid = userid;
		this.cardtype = cardtype;
		this.expiryDate = expirydate;
		this.cvc = cvc;
	}

    public CardBean(){
    	
    }
}
