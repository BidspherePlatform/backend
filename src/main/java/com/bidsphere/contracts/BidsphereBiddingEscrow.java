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
    public static final String BINARY = "608060405234801561000f575f5ffd5b50604051611641380380611641833981810160405281019061003191906100d4565b805f5f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550506100ff565b5f5ffd5b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f6100a38261007a565b9050919050565b6100b381610099565b81146100bd575f5ffd5b50565b5f815190506100ce816100aa565b92915050565b5f602082840312156100e9576100e8610076565b5b5f6100f6848285016100c0565b91505092915050565b6115358061010c5f395ff3fe608060405260043610610085575f3560e01c80635b35b980116100585780635b35b9801461013f578063805547c5146101675780639d31c9b7146101a85780639dad41a0146101e4578063c18a7a431461020057610085565b80630181aa1414610089578063206a5ff2146100b157806336379b04146100ed5780635129deb814610115575b5f5ffd5b348015610094575f5ffd5b506100af60048036038101906100aa9190610e66565b61023c565b005b3480156100bc575f5ffd5b506100d760048036038101906100d29190610e66565b6105f2565b6040516100e49190610eab565b60405180910390f35b3480156100f8575f5ffd5b50610113600480360381019061010e9190610f1e565b61060f565b005b348015610120575f5ffd5b5061012961074c565b6040516101369190610f6b565b60405180910390f35b34801561014a575f5ffd5b5061016560048036038101906101609190610fb7565b610770565b005b348015610172575f5ffd5b5061018d60048036038101906101889190610e66565b6109c6565b60405161019f96959493929190611039565b60405180910390f35b3480156101b3575f5ffd5b506101ce60048036038101906101c99190610e66565b610a54565b6040516101db9190610f6b565b60405180910390f35b6101fe60048036038101906101f99190611098565b610ab3565b005b34801561020b575f5ffd5b5061022660048036038101906102219190610e66565b610ddd565b6040516102339190610f6b565b60405180910390f35b5f60025f836fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f209050806005015f9054906101000a900460ff16156102c7576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016102be90611130565b60405180910390fd5b6001816005015f6101000a81548160ff021916908315150217905550806001015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166108fc826004015490811502906040515f60405180830381858888f1935050505015801561034d573d5f5f3e3d5ffd5b50806003015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1660015f835f015f9054906101000a900460801b6fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f205f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505f5f90505b81600701805490508110156105ed575f8260070182815481106104205761041f61114e565b5b905f5260205f20015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050826003015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff16141580156104f55750826006015f8273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f206001015f9054906101000a900460ff16155b156105df575f836006015f8373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f205f015490506001846006015f8473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f206001015f6101000a81548160ff0219169083151502179055508173ffffffffffffffffffffffffffffffffffffffff166108fc8290811502906040515f60405180830381858888f193505050501580156105dc573d5f5f3e3d5ffd5b50505b5080806001019150506103fa565b505050565b6003602052805f5260405f205f915054906101000a900460ff1681565b5f73ffffffffffffffffffffffffffffffffffffffff1660015f846fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f205f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16146106d3576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016106ca906111c5565b60405180910390fd5b8060015f846fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f205f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505050565b5f5f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60035f856fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f205f9054906101000a900460ff16156107f4576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016107eb9061122d565b60405180910390fd5b8173ffffffffffffffffffffffffffffffffffffffff1660015f856fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f205f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16146108b8576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016108af90611295565b60405180910390fd5b5f60025f866fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f20905083815f015f6101000a8154816fffffffffffffffffffffffffffffffff021916908360801c021790555082816001015f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550818160020181905550818160040181905550600160035f876fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f205f6101000a81548160ff0219169083151502179055505050505050565b6002602052805f5260405f205f91509050805f015f9054906101000a900460801b90806001015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690806002015490806003015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690806004015490806005015f9054906101000a900460ff16905086565b5f60015f836fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f205f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050919050565b8160025f826fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f206005015f9054906101000a900460ff1615610b3b576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610b32906112fd565b60405180910390fd5b5f60025f856fffffffffffffffffffffffffffffffff19166fffffffffffffffffffffffffffffffff191681526020019081526020015f2090505f816006015f3373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020015f209050805f01548411610bfc576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610bf39061138b565b60405180910390fd5b81600401548411610c42576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610c3990611419565b60405180910390fd5b5f815f015485610c529190611464565b9050803414610c96576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610c8d906114e1565b60405180910390fd5b5f825f015403610d03578260070133908060018154018082558091505060019003905f5260205f20015f9091909190916101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505b84825f01819055505f826001015f6101000a81548160ff0219169083151502179055505f5f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166108fc8290811502906040515f60405180830381858888f19350505050158015610d89573d5f5f3e3d5ffd5b5084836004018190555033836003015f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550505050505050565b6001602052805f5260405f205f915054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b5f5ffd5b5f7fffffffffffffffffffffffffffffffff0000000000000000000000000000000082169050919050565b610e4581610e11565b8114610e4f575f5ffd5b50565b5f81359050610e6081610e3c565b92915050565b5f60208284031215610e7b57610e7a610e0d565b5b5f610e8884828501610e52565b91505092915050565b5f8115159050919050565b610ea581610e91565b82525050565b5f602082019050610ebe5f830184610e9c565b92915050565b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f610eed82610ec4565b9050919050565b610efd81610ee3565b8114610f07575f5ffd5b50565b5f81359050610f1881610ef4565b92915050565b5f5f60408385031215610f3457610f33610e0d565b5b5f610f4185828601610e52565b9250506020610f5285828601610f0a565b9150509250929050565b610f6581610ee3565b82525050565b5f602082019050610f7e5f830184610f5c565b92915050565b5f819050919050565b610f9681610f84565b8114610fa0575f5ffd5b50565b5f81359050610fb181610f8d565b92915050565b5f5f5f5f60808587031215610fcf57610fce610e0d565b5b5f610fdc87828801610e52565b9450506020610fed87828801610e52565b9350506040610ffe87828801610f0a565b925050606061100f87828801610fa3565b91505092959194509250565b61102481610e11565b82525050565b61103381610f84565b82525050565b5f60c08201905061104c5f83018961101b565b6110596020830188610f5c565b611066604083018761102a565b6110736060830186610f5c565b611080608083018561102a565b61108d60a0830184610e9c565b979650505050505050565b5f5f604083850312156110ae576110ad610e0d565b5b5f6110bb85828601610e52565b92505060206110cc85828601610fa3565b9150509250929050565b5f82825260208201905092915050565b7f4c697374696e6720616c726561647920656e64656400000000000000000000005f82015250565b5f61111a6015836110d6565b9150611125826110e6565b602082019050919050565b5f6020820190508181035f8301526111478161110e565b9050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52603260045260245ffd5b7f50726f6475637420616c726561647920726567697374657265640000000000005f82015250565b5f6111af601a836110d6565b91506111ba8261117b565b602082019050919050565b5f6020820190508181035f8301526111dc816111a3565b9050919050565b7f4c697374696e6720616c726561647920657869737473000000000000000000005f82015250565b5f6112176016836110d6565b9150611222826111e3565b602082019050919050565b5f6020820190508181035f8301526112448161120b565b9050919050565b7f53656c6c6572206d757374206f776e207468652070726f6475637400000000005f82015250565b5f61127f601b836110d6565b915061128a8261124b565b602082019050919050565b5f6020820190508181035f8301526112ac81611273565b9050919050565b7f41756374696f6e20616c726561647920656e64656400000000000000000000005f82015250565b5f6112e76015836110d6565b91506112f2826112b3565b602082019050919050565b5f6020820190508181035f830152611314816112db565b9050919050565b7f4e657720626964206d75737420626520686967686572207468616e20707265765f8201527f696f757320626964000000000000000000000000000000000000000000000000602082015250565b5f6113756028836110d6565b91506113808261131b565b604082019050919050565b5f6020820190508181035f8301526113a281611369565b9050919050565b7f426964206d75737420626520686967686572207468616e2063757272656e74205f8201527f6869676865737400000000000000000000000000000000000000000000000000602082015250565b5f6114036027836110d6565b915061140e826113a9565b604082019050919050565b5f6020820190508181035f830152611430816113f7565b9050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52601160045260245ffd5b5f61146e82610f84565b915061147983610f84565b925082820390508181111561149157611490611437565b5b92915050565b7f53656e642074686520636f727265637420646966666572656e6365206f6e6c795f82015250565b5f6114cb6020836110d6565b91506114d682611497565b602082019050919050565b5f6020820190508181035f8301526114f8816114bf565b905091905056fea26469706673582212204e8c4fe181aa1cced1b5dc8eeba1c2966d92ccf65bfbb7698e5a74de073fbc4d64736f6c634300081e0033";

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
