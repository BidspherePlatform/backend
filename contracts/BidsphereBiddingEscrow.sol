// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract BidsphereBiddingEscrow {
    struct Bid {
        uint256 amount;
        bool refunded;
    }

    struct Listing {
        bytes16 productId;
        address sellerWallet;
        uint256 startingPrice;
        address highestBidder;
        uint256 highestBid;
        bool ended;
        mapping(address => Bid) bids;
        address[] bidders;
    }

    address public holdingAccount;

    mapping(bytes16 => address) public productOwner;

    mapping(bytes16 => Listing) public listings;
    mapping(bytes16 => bool) public initialized;

    constructor(address _holdingAccount) {
        holdingAccount = _holdingAccount;
    }

    modifier onlyBeforeEnd(bytes16 listingId) {
        require(!listings[listingId].ended, "Auction already ended");
        _;
    }

    function registerProduct(bytes16 productId, address owner) external {
        require(productOwner[productId] == address(0), "Product already registered");
        productOwner[productId] = owner;
    }

    function createListing(
        bytes16 listingId,
        bytes16 productId,
        address sellerWallet,
        uint256 startingPrice
    ) external {
        require(productOwner[productId] != address(0), "Product does not exist");
        require(!initialized[listingId], "Listing already exists");
        require(productOwner[productId] == sellerWallet, "Seller must own the product");
        require(startingPrice > 0, "Starting price must be greater than zero");


        Listing storage l = listings[listingId];
        l.productId = productId;
        l.sellerWallet = sellerWallet;
        l.startingPrice = startingPrice;
        l.highestBid = startingPrice;

        initialized[listingId] = true;
    }

    function placeBid(bytes16 listingId, uint256 bidAmount) external payable onlyBeforeEnd(listingId) {
        require(initialized[listingId], "Listing does not exist");
        Listing storage listing = listings[listingId];
        Bid storage previous = listing.bids[msg.sender];

        require(msg.sender != listing.sellerWallet, "Seller cannot bid on own listing");
        require(bidAmount > previous.amount, "New bid must be higher than previous bid");
        require(bidAmount > listing.highestBid, "Bid must be higher than current highest");

        uint256 requiredPayment = bidAmount - previous.amount;
        require(msg.value == requiredPayment, "Send the correct difference only");

        if (previous.amount == 0) {
            listing.bidders.push(msg.sender);
        }

        previous.amount = bidAmount;
        previous.refunded = false;

        payable(holdingAccount).transfer(requiredPayment);

        listing.highestBid = bidAmount;
        listing.highestBidder = msg.sender;
    }

    function endListing(bytes16 listingId) external {
        Listing storage listing = listings[listingId];
        require(!listing.ended, "Listing already ended");

        listing.ended = true;

        payable(listing.sellerWallet).transfer(listing.highestBid);

        productOwner[listing.productId] = listing.highestBidder;

        for (uint i = 0; i < listing.bidders.length; i++) {
            address bidder = listing.bidders[i];
            if (bidder != listing.highestBidder && !listing.bids[bidder].refunded) {
                uint256 refund = listing.bids[bidder].amount;
                listing.bids[bidder].refunded = true;
                payable(bidder).transfer(refund);
            }
        }
    }

    function getCurrentOwner(bytes16 productId) external view returns (address) {
        return productOwner[productId];
    }
}