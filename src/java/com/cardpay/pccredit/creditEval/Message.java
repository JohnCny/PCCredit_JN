package com.cardpay.pccredit.creditEval;

public class Message
{
  private double top;
  private double bottom;
  private int flag;
  private String refuseReason;
  
  public double getTop()
  {
    return this.top;
  }
  
  public void setTop(double top)
  {
    this.top = top;
  }
  
  public double getBottom()
  {
    return this.bottom;
  }
  
  public void setBottom(double bottom)
  {
    this.bottom = bottom;
  }
  
  public int getFlag()
  {
    return this.flag;
  }
  
  public void setFlag(int flag)
  {
    this.flag = flag;
  }
  
  public String getRefuseReason()
  {
    return this.refuseReason;
  }
  
  public void setRefuseReason(String refuseReason)
  {
    this.refuseReason = refuseReason;
  }
  
  public Message() {}
  
  public Message(double top, double bottom, int flag, String refuseReason)
  {
    this.top = top;
    this.bottom = bottom;
    this.flag = flag;
    this.refuseReason = refuseReason;
  }
  
  public String toString()
  {
    String flagString = this.flag == 0 ? "Success!" : "Error!";
    if (this.flag == 0) {
      return flagString + "\t" + "Credit interval: [" + this.bottom + ", " + this.top + "]";
    }
    return flagString + "\t" + this.refuseReason;
  }
}
