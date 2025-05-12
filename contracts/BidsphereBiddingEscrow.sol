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
        address lastBidder;
        bool ended;
        mapping(address => Bid) bids;
        address[] bidders;
    }

    mapping(bytes16 => address) public productOwner;
    mapping(bytes16 => Listing) public listings;
    mapping(bytes16 => bool) public initialized;

    constructor() {}

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

    function placeBid(bytes16 listingId) external payable onlyBeforeEnd(listingId) {
        require(initialized[listingId], "Listing does not exist");
        Listing storage listing = listings[listingId];
        Bid storage previous = listing.bids[msg.sender];

        require(msg.value > 0, "Bid must be greater than zero");
        require(msg.sender != listing.sellerWallet, "Seller cannot bid on own listing");
        require(msg.sender != listing.lastBidder, "Bidder must not create consecutive bids");
        require(msg.value > previous.amount, "New bid must be higher than previous bid");
        require(msg.value > listing.highestBid, "Bid must be higher than current highest");

        if (previous.amount == 0) {
            listing.bidders.push(msg.sender);
        } else {
            payable(msg.sender).transfer(previous.amount);
        }

        previous.amount = msg.value;
        previous.refunded = false;

        listing.highestBid = msg.value;
        listing.highestBidder = msg.sender;
        listing.lastBidder = msg.sender;
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