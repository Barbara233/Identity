package com.identity.domain;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple5;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.3.0.
 */
public class Identity extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b506000808055600355610e2d806100286000396000f3006080604052600436106100985763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416630a3b0a4f811461009d5780631a695230146100d05780631f3c99c3146100e657806351f91066146100fb578063774caefe14610110578063a87430ba146101ad578063dc510ecf146102c6578063e4092c5f14610363578063ec3a6f73146103be575b600080fd5b3480156100a957600080fd5b506100be600160a060020a03600435166103d3565b60408051918252519081900360200190f35b6100e4600160a060020a03600435166103e5565b005b3480156100f257600080fd5b506100e461045d565b34801561010757600080fd5b506100be610560565b34801561011c57600080fd5b506040805160206004803580820135601f81018490048402850184019095528484526100e494369492936024939284019190819084018382808284375050604080516020601f818a01358b0180359182018390048302840183018552818452989b8a359b909a9099940197509195509182019350915081908401838280828437509497506105669650505050505050565b3480156101b957600080fd5b506101ce600160a060020a036004351661073d565b6040805160208082018790528415156060830152831515608083015260a08083528851908301528751919283929083019160c0840191908a019080838360005b8381101561022657818101518382015260200161020e565b50505050905090810190601f1680156102535780820380516001836020036101000a031916815260200191505b50838103825286518152865160209182019188019080838360005b8381101561028657818101518382015260200161026e565b50505050905090810190601f1680156102b35780820380516001836020036101000a031916815260200191505b5097505050505050505060405180910390f35b3480156102d257600080fd5b506040805160206004803580820135601f81018490048402850184019095528484526100e494369492936024939284019190819084018382808284375050604080516020601f818a01358b0180359182018390048302840183018552818452989b8a359b909a90999401975091955091820193509150819084018382808284375094975061088f9650505050505050565b34801561036f57600080fd5b506040805160206004803580820135601f81018490048402850184019095528484526100e49436949293602493928401919081908401838280828437509497505093359450610c2c9350505050565b3480156103ca57600080fd5b506100be610d1c565b60026020526000908152604090205481565b600034333103106104545760405133903480156108fc02916000818181858888f19350505050151561041657600080fd5b604051600160a060020a038216907f2d339b1e3334d3f43e7aa6d5b1fa3ca5e4228b2dd547d1710c726019d98e53fa90600090a2600160035561045a565b60006003555b50565b336000908152600260205260409020541580159061048d57503360009081526001602052604090206003015460ff165b80156104b0575033600090815260016020526040902060030154610100900460ff165b1561055e57336000908152600160205260408120906104cf8282610d22565b60018201600090556002820160006104e79190610d22565b506003908101805461ffff1916905533600090815260026020908152604080832083905560019091529020015460ff16151561055857600080546000190181556040517fd2ecf4f83757b136e9c73220082157c2afe4cb64c4b8dd6847ca4beefa06d6b19190a1600160035561055e565b60006003555b565b60035481565b336000908152600260205260409020541580159061059c57503360009081526001602081905260409091206003015460ff161515145b156105ab576000600355610738565b33600090815260016020908152604090912084516105cb92860190610d66565b5033600090815260016020818152604090922090810184905582516105f892600290920191840190610d66565b50336000908152600160208181526040808420600301805460ff19168417905583548301845560028252808420929092558151808201869052606080825287519082015286517ff348d6aef493648acabb44f7df3871e27b0b8a601a3e4d4ee6c277d1951db5d694889488948894938493928401926080850192890191908190849084905b8381101561069557818101518382015260200161067d565b50505050905090810190601f1680156106c25780820380516001836020036101000a031916815260200191505b50838103825284518152845160209182019186019080838360005b838110156106f55781810151838201526020016106dd565b50505050905090810190601f1680156107225780820380516001836020036101000a031916815260200191505b509550505050505060405180910390a160016003555b505050565b60016020818152600092835260409283902080548451600294821615610100026000190190911693909304601f81018390048302840183019094528383529283918301828280156107cf5780601f106107a4576101008083540402835291602001916107cf565b820191906000526020600020905b8154815290600101906020018083116107b257829003601f168201915b505050505090806001015490806002018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156108735780601f1061084857610100808354040283529160200191610873565b820191906000526020600020905b81548152906001019060200180831161085657829003601f168201915b5050506003909301549192505060ff8082169161010090041685565b33600090815260026020526040902054158015906108c557503360009081526001602081905260409091206003015460ff161515145b15610c2257826040518082805190602001908083835b602083106108fa5780518252601f1990920191602091820191016108db565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040518091039020600019166001600033600160a060020a0316600160a060020a0316815260200190815260200160002060000160405180828054600181600116156101000203166002900480156109ae5780601f1061098c5761010080835404028352918201916109ae565b820191906000526020600020905b81548152906001019060200180831161099a575b50509150506040518091039020600019161480156109de5750336000908152600160208190526040909120015482145b8015610adc5750806040518082805190602001908083835b60208310610a155780518252601f1990920191602091820191016109f6565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040518091039020600019166001600033600160a060020a0316600160a060020a031681526020019081526020016000206002016040518082805460018160011615610100020316600290048015610ac95780601f10610aa7576101008083540402835291820191610ac9565b820191906000526020600020905b815481529060010190602001808311610ab5575b5050915050604051809103902060001916145b15610c1757336000908152600160209081526040808320600301805461ff0019166101001790558051808301869052606080825287519082015286517ff0079195989b40d0558a23d04a4a1165c80e238eb94d12a4f95f0ab01d6ff9ed9488948894889493849391840192608085019290890191908190849084905b83811015610b70578181015183820152602001610b58565b50505050905090810190601f168015610b9d5780820380516001836020036101000a031916815260200191505b50838103825284518152845160209182019186019080838360005b83811015610bd0578181015183820152602001610bb8565b50505050905090810190601f168015610bfd5780820380516001836020036101000a031916815260200191505b509550505050505060405180910390a16001600355610c1d565b60006003555b610738565b6000600355505050565b3360009081526001602090815260409091208351610c4c92850190610d66565b50604080518281528151602091819003820181203360009081526001808552858220019190915582820185905283825285519382019390935284517f6a9f8e5bed4084a70b858bfcadf1265a0eb4463b85a0aa9e31e0749ecd749ae493869386939283926060840192870191908190849084905b83811015610cd8578181015183820152602001610cc0565b50505050905090810190601f168015610d055780820380516001836020036101000a031916815260200191505b50935050505060405180910390a150506001600355565b60005481565b50805460018160011615610100020316600290046000825580601f10610d48575061045a565b601f01602090049060005260206000209081019061045a9190610de4565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610da757805160ff1916838001178555610dd4565b82800160010185558215610dd4579182015b82811115610dd4578251825591602001919060010190610db9565b50610de0929150610de4565b5090565b610dfe91905b80821115610de05760008155600101610dea565b905600a165627a7a723058202f1bbba63f2396e88b290b0ba5dc0f3e33590c5c118fcd40d612a2c251be345c0029";

    protected Identity(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Identity(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<RegisterEventResponse> getRegisterEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("Register", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Utf8String>() {}));
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(event, transactionReceipt);
        ArrayList<RegisterEventResponse> responses = new ArrayList<RegisterEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RegisterEventResponse typedResponse = new RegisterEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.name = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.password = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.role = (String) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<RegisterEventResponse> registerEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("Register", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Utf8String>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, RegisterEventResponse>() {
            @Override
            public RegisterEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(event, log);
                RegisterEventResponse typedResponse = new RegisterEventResponse();
                typedResponse.log = log;
                typedResponse.name = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.password = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.role = (String) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public List<LoginEventResponse> getLoginEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("Login", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Utf8String>() {}));
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(event, transactionReceipt);
        ArrayList<LoginEventResponse> responses = new ArrayList<LoginEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            LoginEventResponse typedResponse = new LoginEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.name = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.password = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.role = (String) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<LoginEventResponse> loginEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("Login", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Utf8String>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, LoginEventResponse>() {
            @Override
            public LoginEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(event, log);
                LoginEventResponse typedResponse = new LoginEventResponse();
                typedResponse.log = log;
                typedResponse.name = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.password = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.role = (String) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public List<ModifyEventResponse> getModifyEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("Modify", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Bytes32>() {}));
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(event, transactionReceipt);
        ArrayList<ModifyEventResponse> responses = new ArrayList<ModifyEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ModifyEventResponse typedResponse = new ModifyEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.newname = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.newpassword = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ModifyEventResponse> modifyEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("Modify", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Bytes32>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, ModifyEventResponse>() {
            @Override
            public ModifyEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(event, log);
                ModifyEventResponse typedResponse = new ModifyEventResponse();
                typedResponse.log = log;
                typedResponse.newname = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.newpassword = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public List<LogoutEventResponse> getLogoutEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("Logout", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList());
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(event, transactionReceipt);
        ArrayList<LogoutEventResponse> responses = new ArrayList<LogoutEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            LogoutEventResponse typedResponse = new LogoutEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<LogoutEventResponse> logoutEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("Logout", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, LogoutEventResponse>() {
            @Override
            public LogoutEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(event, log);
                LogoutEventResponse typedResponse = new LogoutEventResponse();
                typedResponse.log = log;
                return typedResponse;
            }
        });
    }

    public List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("Transfer", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(event, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.receiver = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<TransferEventResponse> transferEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("Transfer", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList());
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, TransferEventResponse>() {
            @Override
            public TransferEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(event, log);
                TransferEventResponse typedResponse = new TransferEventResponse();
                typedResponse.log = log;
                typedResponse.receiver = (String) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public RemoteCall<BigInteger> add(String param0) {
        Function function = new Function("add", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> transfer(String receiver, BigInteger weiValue) {
        Function function = new Function(
                "transfer", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(receiver)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<TransactionReceipt> logout() {
        Function function = new Function(
                "logout", 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> tag() {
        Function function = new Function("tag", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> register(String name, byte[] password, String role) {
        Function function = new Function(
                "register", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(name), 
                new org.web3j.abi.datatypes.generated.Bytes32(password), 
                new org.web3j.abi.datatypes.Utf8String(role)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple5<String, byte[], String, Boolean, Boolean>> users(String param0) {
        final Function function = new Function("users", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Bool>() {}, new TypeReference<Bool>() {}));
        return new RemoteCall<Tuple5<String, byte[], String, Boolean, Boolean>>(
                new Callable<Tuple5<String, byte[], String, Boolean, Boolean>>() {
                    @Override
                    public Tuple5<String, byte[], String, Boolean, Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<String, byte[], String, Boolean, Boolean>(
                                (String) results.get(0).getValue(), 
                                (byte[]) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (Boolean) results.get(3).getValue(), 
                                (Boolean) results.get(4).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> login(String name, byte[] password, String role) {
        Function function = new Function(
                "login", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(name), 
                new org.web3j.abi.datatypes.generated.Bytes32(password), 
                new org.web3j.abi.datatypes.Utf8String(role)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> modify(String newname, byte[] newpassword) {
        Function function = new Function(
                "modify", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(newname), 
                new org.web3j.abi.datatypes.generated.Bytes32(newpassword)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> numRegistrants() {
        Function function = new Function("numRegistrants", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public static RemoteCall<Identity> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Identity.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Identity> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Identity.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static Identity load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Identity(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Identity load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Identity(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class RegisterEventResponse {
        public Log log;

        public String name;

        public byte[] password;

        public String role;
    }

    public static class LoginEventResponse {
        public Log log;

        public String name;

        public byte[] password;

        public String role;
    }

    public static class ModifyEventResponse {
        public Log log;

        public String newname;

        public byte[] newpassword;
    }

    public static class LogoutEventResponse {
        public Log log;
    }

    public static class TransferEventResponse {
        public Log log;

        public String receiver;
    }
}
