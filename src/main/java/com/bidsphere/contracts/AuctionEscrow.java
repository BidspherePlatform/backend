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
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tuples.generated.Tuple4;
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
public class AuctionEscrow extends Contract {
    public static final String BINARY = "60806040525f6001553480156012575f5ffd5b50335f5f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550610f488061005f5f395ff3fe60806040526004361061007a575f3560e01c80638da5cb5b1161004d5780638da5cb5b146101495780639979ef45146101735780639ace38c21461018f578063de74e57b146101ce5761007a565b806305261aea1461007e578063111cb258146100a65780631223a3e5146100e35780636c2c9c7d1461011f575b5f5ffd5b348015610089575f5ffd5b506100a4600480360381019061009f9190610a78565b61020c565b005b3480156100b1575f5ffd5b506100cc60048036038101906100c79190610a78565b610577565b6040516100da929190610af1565b60405180910390f35b3480156100ee575f5ffd5b5061010960048036038101906101049190610b75565b6105b6565b6040516101169190610bb3565b60405180910390f35b34801561012a575f5ffd5b5061013361071e565b6040516101409190610bb3565b60405180910390f35b348015610154575f5ffd5b5061015d610724565b60405161016a9190610bcc565b60405180910390f35b61018d60048036038101906101889190610a78565b610748565b005b34801561019a575f5ffd5b506101b560048036038101906101b09190610a78565b610986565b6040516101c59493929190610bf4565b60405180910390f35b3480156101d9575f5ffd5b506101f460048036038101906101ef9190610a78565b6109f0565b60405161020393929190610c51565b60405180910390f35b5f5f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161461029a576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161029190610ce0565b60405180910390fd5b5f60025f8381526020019081526020015f209050806002015f9054906101000a900460ff166102fe576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016102f590610d48565b60405180910390fd5b5f60035f8481526020019081526020015f206040518060400160405290815f82015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200160018201548152505090505f8160200151116103c1576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016103b890610db0565b60405180910390fd5b5f826002015f6101000a81548160ff021916908315150217905550815f015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166108fc826020015190811502906040515f60405180830381858888f19350505050158015610445573d5f5f3e3d5ffd5b506040518060800160405280835f015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001825f015173ffffffffffffffffffffffffffffffffffffffff16815260200183600101548152602001826020015181525060045f8581526020019081526020015f205f820151815f015f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506020820151816001015f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506040820151816002015560608201518160030155905050505050565b6003602052805f5260405f205f91509050805f015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060010154905082565b5f5f5f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610645576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161063c90610ce0565b60405180910390fd5b60015f81548092919061065790610dfb565b919050555060405180606001604052808473ffffffffffffffffffffffffffffffffffffffff1681526020018381526020016001151581525060025f60015481526020019081526020015f205f820151815f015f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550602082015181600101556040820151816002015f6101000a81548160ff021916908315150217905550905050600154905092915050565b60015481565b5f5f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b5f60025f8381526020019081526020015f206040518060600160405290815f82015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200160018201548152602001600282015f9054906101000a900460ff16151515158152505090508060400151610823576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161081a90610e8c565b60405180910390fd5b5f60035f8481526020019081526020015f2090508060010154341161087d576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161087490610ef4565b60405180910390fd5b5f816001015411156108f457805f015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166108fc826001015490811502906040515f60405180830381858888f193505050501580156108f2573d5f5f3e3d5ffd5b505b60405180604001604052803373ffffffffffffffffffffffffffffffffffffffff1681526020013481525060035f8581526020019081526020015f205f820151815f015f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060208201518160010155905050505050565b6004602052805f5260405f205f91509050805f015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690806001015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060020154908060030154905084565b6002602052805f5260405f205f91509050805f015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690806001015490806002015f9054906101000a900460ff16905083565b5f5ffd5b5f819050919050565b610a5781610a45565b8114610a61575f5ffd5b50565b5f81359050610a7281610a4e565b92915050565b5f60208284031215610a8d57610a8c610a41565b5b5f610a9a84828501610a64565b91505092915050565b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f610acc82610aa3565b9050919050565b610adc81610ac2565b82525050565b610aeb81610a45565b82525050565b5f604082019050610b045f830185610ad3565b610b116020830184610ae2565b9392505050565b610b2181610ac2565b8114610b2b575f5ffd5b50565b5f81359050610b3c81610b18565b92915050565b5f819050919050565b610b5481610b42565b8114610b5e575f5ffd5b50565b5f81359050610b6f81610b4b565b92915050565b5f5f60408385031215610b8b57610b8a610a41565b5b5f610b9885828601610b2e565b9250506020610ba985828601610b61565b9150509250929050565b5f602082019050610bc65f830184610ae2565b92915050565b5f602082019050610bdf5f830184610ad3565b92915050565b610bee81610b42565b82525050565b5f608082019050610c075f830187610ad3565b610c146020830186610ad3565b610c216040830185610be5565b610c2e6060830184610ae2565b95945050505050565b5f8115159050919050565b610c4b81610c37565b82525050565b5f606082019050610c645f830186610ad3565b610c716020830185610be5565b610c7e6040830184610c42565b949350505050565b5f82825260208201905092915050565b7f4f6e6c79206261636b656e6420616c6c6f7765640000000000000000000000005f82015250565b5f610cca601483610c86565b9150610cd582610c96565b602082019050919050565b5f6020820190508181035f830152610cf781610cbe565b9050919050565b7f4c697374696e6720616c726561647920636c6f736564000000000000000000005f82015250565b5f610d32601683610c86565b9150610d3d82610cfe565b602082019050919050565b5f6020820190508181035f830152610d5f81610d26565b9050919050565b7f4e6f2076616c69642062696473000000000000000000000000000000000000005f82015250565b5f610d9a600d83610c86565b9150610da582610d66565b602082019050919050565b5f6020820190508181035f830152610dc781610d8e565b9050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52601160045260245ffd5b5f610e0582610a45565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8203610e3757610e36610dce565b5b600182019050919050565b7f496e76616c6964206c697374696e6700000000000000000000000000000000005f82015250565b5f610e76600f83610c86565b9150610e8182610e42565b602082019050919050565b5f6020820190508181035f830152610ea381610e6a565b9050919050565b7f4d757374206f75746269642063757272656e74206869676865737400000000005f82015250565b5f610ede601b83610c86565b9150610ee982610eaa565b602082019050919050565b5f6020820190508181035f830152610f0b81610ed2565b905091905056fea2646970667358221220f60371871b5964ffafdb658cdc34d91279f9f0968b9e962ae3cbab7449f9acf564736f6c634300081d0033";

    private static String librariesLinkedBinary;

    public static final String FUNC_CREATELISTING = "createListing";

    public static final String FUNC_FINALIZE = "finalize";

    public static final String FUNC_HIGHESTBIDS = "highestBids";

    public static final String FUNC_LISTINGCOUNTER = "listingCounter";

    public static final String FUNC_LISTINGS = "listings";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_PLACEBID = "placeBid";

    public static final String FUNC_TRANSACTIONS = "transactions";

    @Deprecated
    protected AuctionEscrow(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected AuctionEscrow(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected AuctionEscrow(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected AuctionEscrow(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> createListing(String seller, byte[] productUUID) {
        final Function function = new Function(
                FUNC_CREATELISTING, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, seller), 
                new org.web3j.abi.datatypes.generated.Bytes32(productUUID)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> finalize(BigInteger listingId) {
        final Function function = new Function(
                FUNC_FINALIZE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(listingId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple2<String, BigInteger>> highestBids(BigInteger param0) {
        final Function function = new Function(FUNC_HIGHESTBIDS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple2<String, BigInteger>>(function,
                new Callable<Tuple2<String, BigInteger>>() {
                    @Override
                    public Tuple2<String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<String, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue());
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> listingCounter() {
        final Function function = new Function(FUNC_LISTINGCOUNTER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple3<String, byte[], Boolean>> listings(BigInteger param0) {
        final Function function = new Function(FUNC_LISTINGS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Bool>() {}));
        return new RemoteFunctionCall<Tuple3<String, byte[], Boolean>>(function,
                new Callable<Tuple3<String, byte[], Boolean>>() {
                    @Override
                    public Tuple3<String, byte[], Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<String, byte[], Boolean>(
                                (String) results.get(0).getValue(), 
                                (byte[]) results.get(1).getValue(), 
                                (Boolean) results.get(2).getValue());
                    }
                });
    }

    public RemoteFunctionCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> placeBid(BigInteger listingId,
            BigInteger weiValue) {
        final Function function = new Function(
                FUNC_PLACEBID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(listingId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<Tuple4<String, String, byte[], BigInteger>> transactions(
            BigInteger param0) {
        final Function function = new Function(FUNC_TRANSACTIONS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple4<String, String, byte[], BigInteger>>(function,
                new Callable<Tuple4<String, String, byte[], BigInteger>>() {
                    @Override
                    public Tuple4<String, String, byte[], BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<String, String, byte[], BigInteger>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (byte[]) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue());
                    }
                });
    }

    @Deprecated
    public static AuctionEscrow load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new AuctionEscrow(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static AuctionEscrow load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new AuctionEscrow(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static AuctionEscrow load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new AuctionEscrow(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static AuctionEscrow load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new AuctionEscrow(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<AuctionEscrow> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(AuctionEscrow.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), "");
    }

    public static RemoteCall<AuctionEscrow> deploy(Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(AuctionEscrow.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<AuctionEscrow> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(AuctionEscrow.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<AuctionEscrow> deploy(Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(AuctionEscrow.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), "");
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
