pragma solidity ^0.8.0;

contract AuctionEscrow {
    struct Listing {
        address seller;
        bytes32 productUUID;
        bool isActive;
    }

    struct BidInfo {
        address bidder;
        uint amount;
    }

    struct TransactionRecord {
        address seller;
        address buyer;
        bytes32 productUUID;
        uint amount;
    }

    address public owner;
    uint public listingCounter = 0;

    mapping(uint => Listing) public listings;
    mapping(uint => BidInfo) public highestBids;
    mapping(uint => TransactionRecord) public transactions;

    modifier onlyOwner() {
        require(msg.sender == owner, "Only backend allowed");
        _;
    }

    constructor() {
        owner = msg.sender;
    }

    function createListing(address seller, bytes32 productUUID) external onlyOwner returns (uint) {
        listingCounter++;
        listings[listingCounter] = Listing(seller, productUUID, true);
        return listingCounter;
    }

    function placeBid(uint listingId) external payable {
        Listing memory listing = listings[listingId];
        require(listing.isActive, "Invalid listing");

        BidInfo storage current = highestBids[listingId];
        require(msg.value > current.amount, "Must outbid current highest");

        if (current.amount > 0) {
            payable(current.bidder).transfer(current.amount);
        }

        highestBids[listingId] = BidInfo({
            bidder: msg.sender,
            amount: msg.value
        });
    }

    function finalize(uint listingId) external onlyOwner {
        Listing storage listing = listings[listingId];
        require(listing.isActive, "Listing already closed");

        BidInfo memory winner = highestBids[listingId];
        require(winner.amount > 0, "No valid bids");

        listing.isActive = false;

        payable(listing.seller).transfer(winner.amount);

        transactions[listingId] = TransactionRecord({
            seller: listing.seller,
            buyer: winner.bidder,
            productUUID: listing.productUUID,
            amount: winner.amount
        });
    }
}
