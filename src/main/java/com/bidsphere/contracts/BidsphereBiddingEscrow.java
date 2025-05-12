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
    public static final String BINARY = "6080604052348015600e575f5ffd5b506119848061001c5f395ff3fe60806040526004361061007a575f3560e01c80635b35b9801161004d5780635b35b98014610126578063805547c51461014e5780639d31c9b714610190578063c18a7a43146101cc5761007a565b80630181aa141461007e578063206a5ff2146100a657806326ec46f9146100e257806336379b04146100fe575b5f5ffd5b348015610089575f5ffd5b506100a4600480360381019061009f91906110f1565b610208565b005b3480156100b1575f5ffd5b506100cc60048036038101906100c791906110f1565b6105bf565b6040516100d99190611136565b60405180910390f35b6100fc60048036038101906100f791906110f1565b6105dc565b005b348015610109575f5ffd5b50610124600480360381019061011f91906111a9565b610ac2565b005b348015610131575f5ffd5b5061014c6004803603810190610147919061121a565b610bfd565b005b348015610159575f5ffd5b50610174600480360381019061016f91906110f1565b610f57565b60405161018797969594939291906112ab565b60405180910390f35b34801561019b575f5ffd5b506101b660048036038101906101b191906110f1565b61100b565b6040516101c39190611318565b60405180910390f35b3480156101d7575f5ffd5b506101f260048036038101906101ed91906110f1565b611069565b6040516101ff9190611318565b60405180910390f35b5f60015f836fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f2090508060050160149054906101000a900460ff1615610294576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161028b9061138b565b60405180910390fd5b60018160050160146101000a81548160ff021916908315150217905550806001015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166108fc826004015490811502906040515f60405180830381858888f1935050505015801561031b573d5f5f3e3d5ffd5b50806003015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff165f5f835f015f9054906101000a900460801b6fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f205f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505f5f90505b81600701805490508110156105ba575f8260070182815481106103ed576103ec6113a9565b5b905f5260205f20015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050826003015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff16141580156104c25750826006015f8273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f206001015f9054906101000a900460ff16155b156105ac575f836006015f8373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f015490506001846006015f8473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f206001015f6101000a81548160ff0219169083151502179055508173ffffffffffffffffffffffffffffffffffffffff166108fc8290811502906040515f60405180830381858888f193505050501580156105a9573d5f5f3e3d5ffd5b50505b5080806001019150506103c7565b505050565b6002602052805f5260405f205f915054906101000a900460ff1681565b8060015f826fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f2060050160149054906101000a900460ff1615610665576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161065c90611420565b60405180910390fd5b60025f836fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f205f9054906101000a900460ff166106e8576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016106df90611488565b60405180910390fd5b5f60015f846fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f2090505f816006015f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f2090505f34116107a6576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161079d906114f0565b60405180910390fd5b816001015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1603610837576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161082e90611558565b60405180910390fd5b816005015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16036108c8576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016108bf906115e6565b60405180910390fd5b805f0154341161090d576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161090490611674565b60405180910390fd5b81600401543411610953576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161094a90611702565b60405180910390fd5b5f815f0154036109c4578160070133908060018154018082558091505060019003905f5260205f20015f9091909190916101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550610a0c565b3373ffffffffffffffffffffffffffffffffffffffff166108fc825f015490811502906040515f60405180830381858888f19350505050158015610a0a573d5f5f3e3d5ffd5b505b34815f01819055505f816001015f6101000a81548160ff02191690831515021790555034826004018190555033826003015f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555033826005015f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050505050565b5f73ffffffffffffffffffffffffffffffffffffffff165f5f846fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f205f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614610b85576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610b7c9061176a565b60405180910390fd5b805f5f846fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f205f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505050565b5f73ffffffffffffffffffffffffffffffffffffffff165f5f856fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f205f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1603610cc0576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610cb7906117d2565b60405180910390fd5b60025f856fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f205f9054906101000a900460ff1615610d44576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610d3b9061183a565b60405180910390fd5b8173ffffffffffffffffffffffffffffffffffffffff165f5f856fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f205f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614610e07576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610dfe906118a2565b60405180910390fd5b5f8111610e49576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610e4090611930565b60405180910390fd5b5f60015f866fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f20905083815f015f6101000a8154816fffffffffffffffffffffffffffffffff021916908360801c021790555082816001015f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550818160020181905550818160040181905550600160025f876fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f205f6101000a81548160ff0219169083151502179055505050505050565b6001602052805f5260405f205f91509050805f015f9054906101000a900460801b90806001015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690806002015490806003015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690806004015490806005015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060050160149054906101000a900460ff16905087565b5f5f5f836fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f205f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050919050565b5f602052805f5260405f205f915054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b5f5ffd5b5f7fffffffffffffffffffffffffffffffff0000000000000000000000000000000082169050919050565b6110d08161109c565b81146110da575f5ffd5b50565b5f813590506110eb816110c7565b92915050565b5f6020828403121561110657611105611098565b5b5f611113848285016110dd565b91505092915050565b5f8115159050919050565b6111308161111c565b82525050565b5f6020820190506111495f830184611127565b92915050565b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f6111788261114f565b9050919050565b6111888161116e565b8114611192575f5ffd5b50565b5f813590506111a38161117f565b92915050565b5f5f604083850312156111bf576111be611098565b5b5f6111cc858286016110dd565b92505060206111dd85828601611195565b9150509250929050565b5f819050919050565b6111f9816111e7565b8114611203575f5ffd5b50565b5f81359050611214816111f0565b92915050565b5f5f5f5f6080858703121561123257611231611098565b5b5f61123f878288016110dd565b9450506020611250878288016110dd565b935050604061126187828801611195565b925050606061127287828801611206565b91505092959194509250565b6112878161109c565b82525050565b6112968161116e565b82525050565b6112a5816111e7565b82525050565b5f60e0820190506112be5f83018a61127e565b6112cb602083018961128d565b6112d8604083018861129c565b6112e5606083018761128d565b6112f2608083018661129c565b6112ff60a083018561128d565b61130c60c0830184611127565b98975050505050505050565b5f60208201905061132b5f83018461128d565b92915050565b5f82825260208201905092915050565b7f4c697374696e6720616c726561647920656e64656400000000000000000000005f82015250565b5f611375601583611331565b915061138082611341565b602082019050919050565b5f6020820190508181035f8301526113a281611369565b9050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52603260045260245ffd5b7f41756374696f6e20616c726561647920656e64656400000000000000000000005f82015250565b5f61140a601583611331565b9150611415826113d6565b602082019050919050565b5f6020820190508181035f830152611437816113fe565b9050919050565b7f4c697374696e6720646f6573206e6f74206578697374000000000000000000005f82015250565b5f611472601683611331565b915061147d8261143e565b602082019050919050565b5f6020820190508181035f83015261149f81611466565b9050919050565b7f426964206d7573742062652067726561746572207468616e207a65726f0000005f82015250565b5f6114da601d83611331565b91506114e5826114a6565b602082019050919050565b5f6020820190508181035f830152611507816114ce565b9050919050565b7f53656c6c65722063616e6e6f7420626964206f6e206f776e206c697374696e675f82015250565b5f611542602083611331565b915061154d8261150e565b602082019050919050565b5f6020820190508181035f83015261156f81611536565b9050919050565b7f426964646572206d757374206e6f742063726561746520636f6e7365637574695f8201527f7665206269647300000000000000000000000000000000000000000000000000602082015250565b5f6115d0602783611331565b91506115db82611576565b604082019050919050565b5f6020820190508181035f8301526115fd816115c4565b9050919050565b7f4e657720626964206d75737420626520686967686572207468616e20707265765f8201527f696f757320626964000000000000000000000000000000000000000000000000602082015250565b5f61165e602883611331565b915061166982611604565b604082019050919050565b5f6020820190508181035f83015261168b81611652565b9050919050565b7f426964206d75737420626520686967686572207468616e2063757272656e74205f8201527f6869676865737400000000000000000000000000000000000000000000000000602082015250565b5f6116ec602783611331565b91506116f782611692565b604082019050919050565b5f6020820190508181035f830152611719816116e0565b9050919050565b7f50726f6475637420616c726561647920726567697374657265640000000000005f82015250565b5f611754601a83611331565b915061175f82611720565b602082019050919050565b5f6020820190508181035f83015261178181611748565b9050919050565b7f50726f6475637420646f6573206e6f74206578697374000000000000000000005f82015250565b5f6117bc601683611331565b91506117c782611788565b602082019050919050565b5f6020820190508181035f8301526117e9816117b0565b9050919050565b7f4c697374696e6720616c726561647920657869737473000000000000000000005f82015250565b5f611824601683611331565b915061182f826117f0565b602082019050919050565b5f6020820190508181035f83015261185181611818565b9050919050565b7f53656c6c6572206d757374206f776e207468652070726f6475637400000000005f82015250565b5f61188c601b83611331565b915061189782611858565b602082019050919050565b5f6020820190508181035f8301526118b981611880565b9050919050565b7f5374617274696e67207072696365206d757374206265206772656174657220745f8201527f68616e207a65726f000000000000000000000000000000000000000000000000602082015250565b5f61191a602883611331565b9150611925826118c0565b604082019050919050565b5f6020820190508181035f8301526119478161190e565b905091905056fea26469706673582212201e3bce039a8981687059a0ea77d58de3b209ccacf3c450f23e06343065614b1c64736f6c634300081e0033";

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

    public RemoteFunctionCall<TransactionReceipt> placeBid(byte[] listingId, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_PLACEBID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes16(listingId)), 
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
