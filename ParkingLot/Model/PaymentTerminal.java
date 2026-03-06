package ParkingLot.Model;

public class PaymentTerminal {

    private int terminalId;
    private String location;

    public PaymentTerminal(int terminalId, String location) {
        this.terminalId = terminalId;
        this.location = location;
    }

    public int getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(int terminalId) {
        this.terminalId = terminalId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void processPayment(Ticket ticket) {
        // Logic to process payment for the given ticket
        System.out.println("Processing payment for ticket ID: " + ticket.getTicketId());
    }
}
