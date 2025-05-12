package com.bidsphere.contracts;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes16;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple7;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/LFDT-web3j/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.7.0.
 */
@SuppressWarnings("rawtypes")
public class BidsphereBiddingEscrow extends Contract {
    public static final String BINARY = "6080604052348015600e575f5ffd5b506119ea8061001c5f395ff3fe60806040526004361061007a575f3560e01c8063805547c51161004d578063805547c5146101325780639d31c9b7146101745780639dad41a0146101b0578063c18a7a43146101cc5761007a565b80630181aa141461007e578063206a5ff2146100a657806336379b04146100e25780635b35b9801461010a575b5f5ffd5b348015610089575f5ffd5b506100a4600480360381019061009f91906110b9565b610208565b005b3480156100b1575f5ffd5b506100cc60048036038101906100c791906110b9565b6105bf565b6040516100d991906110fe565b60405180910390f35b3480156100ed575f5ffd5b5061010860048036038101906101039190611171565b6105dc565b005b348015610115575f5ffd5b50610130600480360381019061012b91906111e2565b610717565b005b34801561013d575f5ffd5b50610158600480360381019061015391906110b9565b610a71565b60405161016b9796959493929190611273565b60405180910390f35b34801561017f575f5ffd5b5061019a600480360381019061019591906110b9565b610b25565b6040516101a791906112e0565b60405180910390f35b6101ca60048036038101906101c591906112f9565b610b83565b005b3480156101d7575f5ffd5b506101f260048036038101906101ed91906110b9565b611031565b6040516101ff91906112e0565b60405180910390f35b5f60015f836fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f2090508060050160149054906101000a900460ff1615610294576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161028b90611391565b60405180910390fd5b60018160050160146101000a81548160ff021916908315150217905550806001015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166108fc826004015490811502906040515f60405180830381858888f1935050505015801561031b573d5f5f3e3d5ffd5b50806003015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff165f5f835f015f9054906101000a900460801b6fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f205f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505f5f90505b81600701805490508110156105ba575f8260070182815481106103ed576103ec6113af565b5b905f5260205f20015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050826003015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff16141580156104c25750826006015f8273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f206001015f9054906101000a900460ff16155b156105ac575f836006015f8373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f015490506001846006015f8473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f206001015f6101000a81548160ff0219169083151502179055508173ffffffffffffffffffffffffffffffffffffffff166108fc8290811502906040515f60405180830381858888f193505050501580156105a9573d5f5f3e3d5ffd5b50505b5080806001019150506103c7565b505050565b6002602052805f5260405f205f915054906101000a900460ff1681565b5f73ffffffffffffffffffffffffffffffffffffffff165f5f846fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f205f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161461069f576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161069690611426565b60405180910390fd5b805f5f846fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f205f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505050565b5f73ffffffffffffffffffffffffffffffffffffffff165f5f856fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f205f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16036107da576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016107d19061148e565b60405180910390fd5b60025f856fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f205f9054906101000a900460ff161561085e576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610855906114f6565b60405180910390fd5b8173ffffffffffffffffffffffffffffffffffffffff165f5f856fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f205f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614610921576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016109189061155e565b60405180910390fd5b5f8111610963576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161095a906115ec565b60405180910390fd5b5f60015f866fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f20905083815f015f6101000a8154816fffffffffffffffffffffffffffffffff021916908360801c021790555082816001015f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550818160020181905550818160040181905550600160025f876fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f205f6101000a81548160ff0219169083151502179055505050505050565b6001602052805f5260405f205f91509050805f015f9054906101000a900460801b90806001015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690806002015490806003015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690806004015490806005015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060050160149054906101000a900460ff16905087565b5f5f5f836fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f205f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050919050565b8160015f826fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f2060050160149054906101000a900460ff1615610c0c576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610c0390611654565b60405180910390fd5b60025f846fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f205f9054906101000a900460ff16610c8f576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610c86906116bc565b60405180910390fd5b5f60015f856fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f2090505f816006015f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f209050816001015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1603610d9c576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610d9390611724565b60405180910390fd5b816005015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1603610e2d576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610e24906117b2565b60405180910390fd5b805f01548411610e72576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610e6990611840565b60405180910390fd5b81600401548411610eb8576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610eaf906118ce565b60405180910390fd5b5f815f015485610ec89190611919565b9050803414610f0c576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610f0390611996565b60405180910390fd5b5f825f015403610f79578260070133908060018154018082558091505060019003905f5260205f20015f9091909190916101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505b84825f01819055505f826001015f6101000a81548160ff02191690831515021790555084836004018190555033836003015f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555033836005015f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550505050505050565b5f602052805f5260405f205f915054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b5f5ffd5b5f7fffffffffffffffffffffffffffffffff0000000000000000000000000000000082169050919050565b61109881611064565b81146110a2575f5ffd5b50565b5f813590506110b38161108f565b92915050565b5f602082840312156110ce576110cd611060565b5b5f6110db848285016110a5565b91505092915050565b5f8115159050919050565b6110f8816110e4565b82525050565b5f6020820190506111115f8301846110ef565b92915050565b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f61114082611117565b9050919050565b61115081611136565b811461115a575f5ffd5b50565b5f8135905061116b81611147565b92915050565b5f5f6040838503121561118757611186611060565b5b5f611194858286016110a5565b92505060206111a58582860161115d565b9150509250929050565b5f819050919050565b6111c1816111af565b81146111cb575f5ffd5b50565b5f813590506111dc816111b8565b92915050565b5f5f5f5f608085870312156111fa576111f9611060565b5b5f611207878288016110a5565b9450506020611218878288016110a5565b93505060406112298782880161115d565b925050606061123a878288016111ce565b91505092959194509250565b61124f81611064565b82525050565b61125e81611136565b82525050565b61126d816111af565b82525050565b5f60e0820190506112865f83018a611246565b6112936020830189611255565b6112a06040830188611264565b6112ad6060830187611255565b6112ba6080830186611264565b6112c760a0830185611255565b6112d460c08301846110ef565b98975050505050505050565b5f6020820190506112f35f830184611255565b92915050565b5f5f6040838503121561130f5761130e611060565b5b5f61131c858286016110a5565b925050602061132d858286016111ce565b9150509250929050565b5f82825260208201905092915050565b7f4c697374696e6720616c726561647920656e64656400000000000000000000005f82015250565b5f61137b601583611337565b915061138682611347565b602082019050919050565b5f6020820190508181035f8301526113a88161136f565b9050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52603260045260245ffd5b7f50726f6475637420616c726561647920726567697374657265640000000000005f82015250565b5f611410601a83611337565b915061141b826113dc565b602082019050919050565b5f6020820190508181035f83015261143d81611404565b9050919050565b7f50726f6475637420646f6573206e6f74206578697374000000000000000000005f82015250565b5f611478601683611337565b915061148382611444565b602082019050919050565b5f6020820190508181035f8301526114a58161146c565b9050919050565b7f4c697374696e6720616c726561647920657869737473000000000000000000005f82015250565b5f6114e0601683611337565b91506114eb826114ac565b602082019050919050565b5f6020820190508181035f83015261150d816114d4565b9050919050565b7f53656c6c6572206d757374206f776e207468652070726f6475637400000000005f82015250565b5f611548601b83611337565b915061155382611514565b602082019050919050565b5f6020820190508181035f8301526115758161153c565b9050919050565b7f5374617274696e67207072696365206d757374206265206772656174657220745f8201527f68616e207a65726f000000000000000000000000000000000000000000000000602082015250565b5f6115d6602883611337565b91506115e18261157c565b604082019050919050565b5f6020820190508181035f830152611603816115ca565b9050919050565b7f41756374696f6e20616c726561647920656e64656400000000000000000000005f82015250565b5f61163e601583611337565b91506116498261160a565b602082019050919050565b5f6020820190508181035f83015261166b81611632565b9050919050565b7f4c697374696e6720646f6573206e6f74206578697374000000000000000000005f82015250565b5f6116a6601683611337565b91506116b182611672565b602082019050919050565b5f6020820190508181035f8301526116d38161169a565b9050919050565b7f53656c6c65722063616e6e6f7420626964206f6e206f776e206c697374696e675f82015250565b5f61170e602083611337565b9150611719826116da565b602082019050919050565b5f6020820190508181035f83015261173b81611702565b9050919050565b7f426964646572206d757374206e6f742063726561746520636f6e7365637574695f8201527f7665206269647300000000000000000000000000000000000000000000000000602082015250565b5f61179c602783611337565b91506117a782611742565b604082019050919050565b5f6020820190508181035f8301526117c981611790565b9050919050565b7f4e657720626964206d75737420626520686967686572207468616e20707265765f8201527f696f757320626964000000000000000000000000000000000000000000000000602082015250565b5f61182a602883611337565b9150611835826117d0565b604082019050919050565b5f6020820190508181035f8301526118578161181e565b9050919050565b7f426964206d75737420626520686967686572207468616e2063757272656e74205f8201527f6869676865737400000000000000000000000000000000000000000000000000602082015250565b5f6118b8602783611337565b91506118c38261185e565b604082019050919050565b5f6020820190508181035f8301526118e5816118ac565b9050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52601160045260245ffd5b5f611923826111af565b915061192e836111af565b9250828203905081811115611946576119456118ec565b5b92915050565b7f53656e642074686520636f727265637420646966666572656e6365206f6e6c795f82015250565b5f611980602083611337565b915061198b8261194c565b602082019050919050565b5f6020820190508181035f8301526119ad81611974565b905091905056fea26469706673582212208c86ff11872e394857a31d9f2ad657b20f79321fb52426030378ce565d13ee5564736f6c634300081e0033";

    private static String librariesLinkedBinary;

    public static final String FUNC_CREATELISTING = "createListing";

    public static final String FUNC_ENDLISTING = "endListing";

    public static final String FUNC_GETCURRENTOWNER = "getCurrentOwner";

    public static final String FUNC_INITIALIZED = "initialized";

    public static final String FUNC_LISTINGS = "listings";

    public static final String FUNC_PLACEBID = "placeBid";

    public static final String FUNC_PRODUCTOWNER = "productOwner";

    public static final String FUNC_REGISTERPRODUCT = "registerProduct";

    @Deprecated
    protected BidsphereBiddingEscrow(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected BidsphereBiddingEscrow(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected BidsphereBiddingEscrow(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected BidsphereBiddingEscrow(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> createListing(byte[] listingId, byte[] productId,
            String sellerWallet, BigInteger startingPrice) {
        final Function function = new Function(
                FUNC_CREATELISTING, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes16(listingId), 
                new org.web3j.abi.datatypes.generated.Bytes16(productId), 
                new org.web3j.abi.datatypes.Address(160, sellerWallet), 
                new org.web3j.abi.datatypes.generated.Uint256(startingPrice)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> endListing(byte[] listingId) {
        final Function function = new Function(
                FUNC_ENDLISTING, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes16(listingId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> getCurrentOwner(byte[] productId) {
        final Function function = new Function(FUNC_GETCURRENTOWNER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes16(productId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Boolean> initialized(byte[] param0) {
        final Function function = new Function(FUNC_INITIALIZED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes16(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Tuple7<byte[], String, BigInteger, String, BigInteger, String, Boolean>> listings(
            byte[] param0) {
        final Function function = new Function(FUNC_LISTINGS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes16(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes16>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Bool>() {}));
        return new RemoteFunctionCall<Tuple7<byte[], String, BigInteger, String, BigInteger, String, Boolean>>(function,
                new Callable<Tuple7<byte[], String, BigInteger, String, BigInteger, String, Boolean>>() {
                    @Override
                    public Tuple7<byte[], String, BigInteger, String, BigInteger, String, Boolean> call(
                            ) throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple7<byte[], String, BigInteger, String, BigInteger, String, Boolean>(
                                (byte[]) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (String) results.get(5).getValue(), 
                                (Boolean) results.get(6).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> placeBid(byte[] listingId, BigInteger bidAmount,
            BigInteger weiValue) {
        final Function function = new Function(
                FUNC_PLACEBID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes16(listingId), 
                new org.web3j.abi.datatypes.generated.Uint256(bidAmount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<String> productOwner(byte[] param0) {
        final Function function = new Function(FUNC_PRODUCTOWNER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes16(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> registerProduct(byte[] productId, String owner) {
        final Function function = new Function(
                FUNC_REGISTERPRODUCT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes16(productId), 
                new org.web3j.abi.datatypes.Address(160, owner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static BidsphereBiddingEscrow load(String contractAddress, Web3j web3j,
            Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new BidsphereBiddingEscrow(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static BidsphereBiddingEscrow load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new BidsphereBiddingEscrow(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static BidsphereBiddingEscrow load(String contractAddress, Web3j web3j,
            Credentials credentials, ContractGasProvider contractGasProvider) {
        return new BidsphereBiddingEscrow(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static BidsphereBiddingEscrow load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new BidsphereBiddingEscrow(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<BidsphereBiddingEscrow> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(BidsphereBiddingEscrow.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), "");
    }

    public static RemoteCall<BidsphereBiddingEscrow> deploy(Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(BidsphereBiddingEscrow.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<BidsphereBiddingEscrow> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(BidsphereBiddingEscrow.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<BidsphereBiddingEscrow> deploy(Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(BidsphereBiddingEscrow.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    public static void linkLibraries(List<Contract.LinkReference> references) {
        librariesLinkedBinary = linkBinaryWithReferences(BINARY, references);
    }

    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }
}
