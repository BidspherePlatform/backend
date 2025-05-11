package com.bidsphere.contracts;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.FunctionEncoder;
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
import org.web3j.tuples.generated.Tuple6;
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
    public static final String BINARY = "608060405234801561000f575f5ffd5b50604051611a21380380611a21833981810160405281019061003191906100d4565b805f5f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550506100ff565b5f5ffd5b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f6100a38261007a565b9050919050565b6100b381610099565b81146100bd575f5ffd5b50565b5f815190506100ce816100aa565b92915050565b5f602082840312156100e9576100e8610076565b5b5f6100f6848285016100c0565b91505092915050565b6119158061010c5f395ff3fe608060405260043610610085575f3560e01c80635b35b980116100585780635b35b9801461013f578063805547c5146101675780639d31c9b7146101a85780639dad41a0146101e4578063c18a7a431461020057610085565b80630181aa1414610089578063206a5ff2146100b157806336379b04146100ed5780635129deb814610115575b5f5ffd5b348015610094575f5ffd5b506100af60048036038101906100aa9190611080565b61023c565b005b3480156100bc575f5ffd5b506100d760048036038101906100d29190611080565b6105f2565b6040516100e491906110c5565b60405180910390f35b3480156100f8575f5ffd5b50610113600480360381019061010e9190611138565b61060f565b005b348015610120575f5ffd5b5061012961074c565b6040516101369190611185565b60405180910390f35b34801561014a575f5ffd5b50610165600480360381019061016091906111d1565b610770565b005b348015610172575f5ffd5b5061018d60048036038101906101889190611080565b610acc565b60405161019f96959493929190611253565b60405180910390f35b3480156101b3575f5ffd5b506101ce60048036038101906101c99190611080565b610b5a565b6040516101db9190611185565b60405180910390f35b6101fe60048036038101906101f991906112b2565b610bb9565b005b34801561020b575f5ffd5b5061022660048036038101906102219190611080565b610ff7565b6040516102339190611185565b60405180910390f35b5f60025f836fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f209050806005015f9054906101000a900460ff16156102c7576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016102be9061134a565b60405180910390fd5b6001816005015f6101000a81548160ff021916908315150217905550806001015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166108fc826004015490811502906040515f60405180830381858888f1935050505015801561034d573d5f5f3e3d5ffd5b50806003015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1660015f835f015f9054906101000a900460801b6fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f205f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505f5f90505b81600701805490508110156105ed575f8260070182815481106104205761041f611368565b5b905f5260205f20015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050826003015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff16141580156104f55750826006015f8273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f206001015f9054906101000a900460ff16155b156105df575f836006015f8373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f015490506001846006015f8473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f206001015f6101000a81548160ff0219169083151502179055508173ffffffffffffffffffffffffffffffffffffffff166108fc8290811502906040515f60405180830381858888f193505050501580156105dc573d5f5f3e3d5ffd5b50505b5080806001019150506103fa565b505050565b6003602052805f5260405f205f915054906101000a900460ff1681565b5f73ffffffffffffffffffffffffffffffffffffffff1660015f846fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f205f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16146106d3576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016106ca906113df565b60405180910390fd5b8060015f846fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f205f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505050565b5f5f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b5f73ffffffffffffffffffffffffffffffffffffffff1660015f856fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f205f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1603610834576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161082b90611447565b60405180910390fd5b60035f856fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f205f9054906101000a900460ff16156108b8576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016108af906114af565b60405180910390fd5b8173ffffffffffffffffffffffffffffffffffffffff1660015f856fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f205f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161461097c576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161097390611517565b60405180910390fd5b5f81116109be576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016109b5906115a5565b60405180910390fd5b5f60025f866fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f20905083815f015f6101000a8154816fffffffffffffffffffffffffffffffff021916908360801c021790555082816001015f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550818160020181905550818160040181905550600160035f876fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f205f6101000a81548160ff0219169083151502179055505050505050565b6002602052805f5260405f205f91509050805f015f9054906101000a900460801b90806001015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690806002015490806003015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690806004015490806005015f9054906101000a900460ff16905086565b5f60015f836fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f205f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050919050565b8160025f826fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f206005015f9054906101000a900460ff1615610c41576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610c389061160d565b60405180910390fd5b60035f846fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f205f9054906101000a900460ff16610cc4576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610cbb90611675565b60405180910390fd5b5f60025f856fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f2090505f816006015f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f209050816001015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1603610dd1576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610dc8906116dd565b60405180910390fd5b805f01548411610e16576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610e0d9061176b565b60405180910390fd5b81600401548411610e5c576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610e53906117f9565b60405180910390fd5b5f815f015485610e6c9190611844565b9050803414610eb0576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610ea7906118c1565b60405180910390fd5b5f825f015403610f1d578260070133908060018154018082558091505060019003905f5260205f20015f9091909190916101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505b84825f01819055505f826001015f6101000a81548160ff0219169083151502179055505f5f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166108fc8290811502906040515f60405180830381858888f19350505050158015610fa3573d5f5f3e3d5ffd5b5084836004018190555033836003015f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550505050505050565b6001602052805f5260405f205f915054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b5f5ffd5b5f7fffffffffffffffffffffffffffffffff0000000000000000000000000000000082169050919050565b61105f8161102b565b8114611069575f5ffd5b50565b5f8135905061107a81611056565b92915050565b5f6020828403121561109557611094611027565b5b5f6110a28482850161106c565b91505092915050565b5f8115159050919050565b6110bf816110ab565b82525050565b5f6020820190506110d85f8301846110b6565b92915050565b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f611107826110de565b9050919050565b611117816110fd565b8114611121575f5ffd5b50565b5f813590506111328161110e565b92915050565b5f5f6040838503121561114e5761114d611027565b5b5f61115b8582860161106c565b925050602061116c85828601611124565b9150509250929050565b61117f816110fd565b82525050565b5f6020820190506111985f830184611176565b92915050565b5f819050919050565b6111b08161119e565b81146111ba575f5ffd5b50565b5f813590506111cb816111a7565b92915050565b5f5f5f5f608085870312156111e9576111e8611027565b5b5f6111f68782880161106c565b94505060206112078782880161106c565b935050604061121887828801611124565b9250506060611229878288016111bd565b91505092959194509250565b61123e8161102b565b82525050565b61124d8161119e565b82525050565b5f60c0820190506112665f830189611235565b6112736020830188611176565b6112806040830187611244565b61128d6060830186611176565b61129a6080830185611244565b6112a760a08301846110b6565b979650505050505050565b5f5f604083850312156112c8576112c7611027565b5b5f6112d58582860161106c565b92505060206112e6858286016111bd565b9150509250929050565b5f82825260208201905092915050565b7f4c697374696e6720616c726561647920656e64656400000000000000000000005f82015250565b5f6113346015836112f0565b915061133f82611300565b602082019050919050565b5f6020820190508181035f83015261136181611328565b9050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52603260045260245ffd5b7f50726f6475637420616c726561647920726567697374657265640000000000005f82015250565b5f6113c9601a836112f0565b91506113d482611395565b602082019050919050565b5f6020820190508181035f8301526113f6816113bd565b9050919050565b7f50726f6475637420646f6573206e6f74206578697374000000000000000000005f82015250565b5f6114316016836112f0565b915061143c826113fd565b602082019050919050565b5f6020820190508181035f83015261145e81611425565b9050919050565b7f4c697374696e6720616c726561647920657869737473000000000000000000005f82015250565b5f6114996016836112f0565b91506114a482611465565b602082019050919050565b5f6020820190508181035f8301526114c68161148d565b9050919050565b7f53656c6c6572206d757374206f776e207468652070726f6475637400000000005f82015250565b5f611501601b836112f0565b915061150c826114cd565b602082019050919050565b5f6020820190508181035f83015261152e816114f5565b9050919050565b7f5374617274696e67207072696365206d757374206265206772656174657220745f8201527f68616e207a65726f000000000000000000000000000000000000000000000000602082015250565b5f61158f6028836112f0565b915061159a82611535565b604082019050919050565b5f6020820190508181035f8301526115bc81611583565b9050919050565b7f41756374696f6e20616c726561647920656e64656400000000000000000000005f82015250565b5f6115f76015836112f0565b9150611602826115c3565b602082019050919050565b5f6020820190508181035f830152611624816115eb565b9050919050565b7f4c697374696e6720646f6573206e6f74206578697374000000000000000000005f82015250565b5f61165f6016836112f0565b915061166a8261162b565b602082019050919050565b5f6020820190508181035f83015261168c81611653565b9050919050565b7f53656c6c65722063616e6e6f7420626964206f6e206f776e206c697374696e675f82015250565b5f6116c76020836112f0565b91506116d282611693565b602082019050919050565b5f6020820190508181035f8301526116f4816116bb565b9050919050565b7f4e657720626964206d75737420626520686967686572207468616e20707265765f8201527f696f757320626964000000000000000000000000000000000000000000000000602082015250565b5f6117556028836112f0565b9150611760826116fb565b604082019050919050565b5f6020820190508181035f83015261178281611749565b9050919050565b7f426964206d75737420626520686967686572207468616e2063757272656e74205f8201527f6869676865737400000000000000000000000000000000000000000000000000602082015250565b5f6117e36027836112f0565b91506117ee82611789565b604082019050919050565b5f6020820190508181035f830152611810816117d7565b9050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52601160045260245ffd5b5f61184e8261119e565b91506118598361119e565b925082820390508181111561187157611870611817565b5b92915050565b7f53656e642074686520636f727265637420646966666572656e6365206f6e6c795f82015250565b5f6118ab6020836112f0565b91506118b682611877565b602082019050919050565b5f6020820190508181035f8301526118d88161189f565b905091905056fea26469706673582212200ca02f49804b29ba3191269126b82af3432e497778bd46ebf13f29f122a5c2ba64736f6c634300081e0033";

    private static String librariesLinkedBinary;

    public static final String FUNC_CREATELISTING = "createListing";

    public static final String FUNC_ENDLISTING = "endListing";

    public static final String FUNC_GETCURRENTOWNER = "getCurrentOwner";

    public static final String FUNC_HOLDINGACCOUNT = "holdingAccount";

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

    public RemoteFunctionCall<String> holdingAccount() {
        final Function function = new Function(FUNC_HOLDINGACCOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Boolean> initialized(byte[] param0) {
        final Function function = new Function(FUNC_INITIALIZED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes16(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Tuple6<byte[], String, BigInteger, String, BigInteger, Boolean>> listings(
            byte[] param0) {
        final Function function = new Function(FUNC_LISTINGS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes16(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes16>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}));
        return new RemoteFunctionCall<Tuple6<byte[], String, BigInteger, String, BigInteger, Boolean>>(function,
                new Callable<Tuple6<byte[], String, BigInteger, String, BigInteger, Boolean>>() {
                    @Override
                    public Tuple6<byte[], String, BigInteger, String, BigInteger, Boolean> call()
                            throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple6<byte[], String, BigInteger, String, BigInteger, Boolean>(
                                (byte[]) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (Boolean) results.get(5).getValue());
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
            ContractGasProvider contractGasProvider, String _holdingAccount) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _holdingAccount)));
        return deployRemoteCall(BidsphereBiddingEscrow.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    public static RemoteCall<BidsphereBiddingEscrow> deploy(Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider,
            String _holdingAccount) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _holdingAccount)));
        return deployRemoteCall(BidsphereBiddingEscrow.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<BidsphereBiddingEscrow> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit, String _holdingAccount) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _holdingAccount)));
        return deployRemoteCall(BidsphereBiddingEscrow.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<BidsphereBiddingEscrow> deploy(Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit,
            String _holdingAccount) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _holdingAccount)));
        return deployRemoteCall(BidsphereBiddingEscrow.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
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
