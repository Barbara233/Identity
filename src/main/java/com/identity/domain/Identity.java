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
 * <p>Generated with web3j version 3.4.0.
 */
public class Identity extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b506000808055600455610cd4806100286000396000f3006080604052600436106100a35763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416630a3b0a4f81146100a85780631a695230146100db5780631f3c99c3146100f157806351f91066146101065780636e07205b1461011b578063774caefe1461014c578063a87430ba146101e9578063dc510ecf14610302578063e4092c5f1461039f578063ec3a6f73146103fa575b600080fd5b3480156100b457600080fd5b506100c9600160a060020a036004351661040f565b60408051918252519081900360200190f35b6100ef600160a060020a0360043516610421565b005b3480156100fd57600080fd5b506100ef6104dd565b34801561011257600080fd5b506100c96105ec565b34801561012757600080fd5b506101306105f2565b60408051600160a060020a039092168252519081900360200190f35b34801561015857600080fd5b506040805160206004803580820135601f81018490048402850184019095528484526100ef94369492936024939284019190819084018382808284375050604080516020601f818a01358b0180359182018390048302840183018552818452989b8a359b909a9099940197509195509182019350915081908401838280828437509497506106019650505050505050565b3480156101f557600080fd5b5061020a600160a060020a0360043516610701565b6040805160208082018790528415156060830152831515608083015260a08083528851908301528751919283929083019160c0840191908a019080838360005b8381101561026257818101518382015260200161024a565b50505050905090810190601f16801561028f5780820380516001836020036101000a031916815260200191505b50838103825286518152865160209182019188019080838360005b838110156102c25781810151838201526020016102aa565b50505050905090810190601f1680156102ef5780820380516001836020036101000a031916815260200191505b5097505050505050505060405180910390f35b34801561030e57600080fd5b506040805160206004803580820135601f81018490048402850184019095528484526100ef94369492936024939284019190819084018382808284375050604080516020601f818a01358b0180359182018390048302840183018552818452989b8a359b909a9099940197509195509182019350915081908401838280828437509497506108539650505050505050565b3480156103ab57600080fd5b506040805160206004803580820135601f81018490048402850184019095528484526100ef9436949293602493928401919081908401838280828437509497505093359450610b449350505050565b34801561040657600080fd5b506100c9610bc3565b60026020526000908152604090205481565b600034333103106104d457604051600160a060020a038216903480156108fc02916000818181858888f19350505050151561045b57600080fd5b60408051338152600160a060020a0383166020820152348183015290517fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef9181900360600190a16003805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a03831617905560016004556104da565b60006004555b50565b336000908152600260205260409020541580159061050d57503360009081526001602052604090206003015460ff165b8015610530575033600090815260016020526040902060030154610100900460ff165b156105ea573360009081526001602052604081209061054f8282610bc9565b60018201600090556002820160006105679190610bc9565b506003908101805461ffff1916905533600090815260026020908152604080832083905560019091529020015460ff1615156105e457600080546000190190556040805133815290517fe7900c232227c6acb496cef22db9716afc7549a305a7f200e07fbb171cc9d6809181900360200190a160016004556105ea565b60006004555b565b60045481565b600354600160a060020a031681565b336000908152600260205260409020541580159061063757503360009081526001602081905260409091206003015460ff161515145b156106465760006004556106fc565b336000908152600160209081526040909120845161066692860190610c0d565b50336000908152600160208181526040909220908101849055825161069392600290920191840190610c0d565b50336000818152600160208181526040808420600301805460ff191684179055835483018455600282529283902091909155815192835290517feeda149c76076b34d4b9d8896c2f7efc0d33d1c7b53ea3c5db490d64613f603a9281900390910190a160016004555b505050565b60016020818152600092835260409283902080548451600294821615610100026000190190911693909304601f81018390048302840183019094528383529283918301828280156107935780601f1061076857610100808354040283529160200191610793565b820191906000526020600020905b81548152906001019060200180831161077657829003601f168201915b505050505090806001015490806002018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156108375780601f1061080c57610100808354040283529160200191610837565b820191906000526020600020905b81548152906001019060200180831161081a57829003601f168201915b5050506003909301549192505060ff8082169161010090041685565b336000908152600260205260409020541580159061088957503360009081526001602081905260409091206003015460ff161515145b15610b3a57826040518082805190602001908083835b602083106108be5780518252601f19909201916020918201910161089f565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040518091039020600019166001600033600160a060020a0316600160a060020a0316815260200190815260200160002060000160405180828054600181600116156101000203166002900480156109725780601f10610950576101008083540402835291820191610972565b820191906000526020600020905b81548152906001019060200180831161095e575b50509150506040518091039020600019161480156109ce575033600090815260016020819052604090912001548214806109ce575060408051838152815160209181900382019020336000908152600192839052929092200154145b8015610acc5750806040518082805190602001908083835b60208310610a055780518252601f1990920191602091820191016109e6565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040518091039020600019166001600033600160a060020a0316600160a060020a031681526020019081526020016000206002016040518082805460018160011615610100020316600290048015610ab95780601f10610a97576101008083540402835291820191610ab9565b820191906000526020600020905b815481529060010190602001808311610aa5575b5050915050604051809103902060001916145b15610b2f5733600081815260016020908152604091829020600301805461ff001916610100179055815192835290517feecc1c95560ca710186ee51249c5b7ba2cb81699a5abefe6d28825017f5786a29281900390910190a16001600455610b35565b60006004555b6106fc565b6000600455505050565b3360009081526001602090815260409091208351610b6492850190610c0d565b50604080518281528151602091819003820181203360008181526001808652908690200191909155815291517fcc49ae3681b668e167a74bea69fc2da02b20cf7a9de11660bf6020b22ed864149281900390910190a150506001600455565b60005481565b50805460018160011615610100020316600290046000825580601f10610bef57506104da565b601f0160209004906000526020600020908101906104da9190610c8b565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610c4e57805160ff1916838001178555610c7b565b82800160010185558215610c7b579182015b82811115610c7b578251825591602001919060010190610c60565b50610c87929150610c8b565b5090565b610ca591905b80821115610c875760008155600101610c91565b905600a165627a7a72305820cb7ff284548de6449319c51614a5383118c8a0e4d022f8c776e3102007e5fd360029";

    public static final String FUNC_ADD = "add";

    public static final String FUNC_TRANSFER = "transfer";

    public static final String FUNC_LOGOUT = "logout";

    public static final String FUNC_TAG = "tag";

    public static final String FUNC_RECEIVER_1 = "receiver_1";

    public static final String FUNC_REGISTER = "register";

    public static final String FUNC_USERS = "users";

    public static final String FUNC_LOGIN = "login";

    public static final String FUNC_MODIFY = "modify";

    public static final String FUNC_NUMREGISTRANTS = "numRegistrants";

    public static final Event REGISTER_EVENT = new Event("Register", 
            Arrays.<TypeReference<?>>asList(),
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final Event LOGIN_EVENT = new Event("Login", 
            Arrays.<TypeReference<?>>asList(),
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final Event MODIFY_EVENT = new Event("Modify", 
            Arrays.<TypeReference<?>>asList(),
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final Event LOGOUT_EVENT = new Event("Logout", 
            Arrays.<TypeReference<?>>asList(),
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final Event TRANSFER_EVENT = new Event("Transfer", 
            Arrays.<TypeReference<?>>asList(),
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    protected Identity(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Identity(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<BigInteger> add(String param0) {
        final Function function = new Function(FUNC_ADD, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> transfer(String receiver, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(receiver)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<TransactionReceipt> logout() {
        final Function function = new Function(
                FUNC_LOGOUT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> tag() {
        final Function function = new Function(FUNC_TAG, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> receiver_1() {
        final Function function = new Function(FUNC_RECEIVER_1, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> register(String name, byte[] password, String role) {
        final Function function = new Function(
                FUNC_REGISTER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(name), 
                new org.web3j.abi.datatypes.generated.Bytes32(password), 
                new org.web3j.abi.datatypes.Utf8String(role)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple5<String, byte[], String, Boolean, Boolean>> users(String param0) {
        final Function function = new Function(FUNC_USERS, 
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
        final Function function = new Function(
                FUNC_LOGIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(name), 
                new org.web3j.abi.datatypes.generated.Bytes32(password), 
                new org.web3j.abi.datatypes.Utf8String(role)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> modify(String newname, byte[] newpassword) {
        final Function function = new Function(
                FUNC_MODIFY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(newname), 
                new org.web3j.abi.datatypes.generated.Bytes32(newpassword)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> numRegistrants() {
        final Function function = new Function(FUNC_NUMREGISTRANTS, 
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

    public List<RegisterEventResponse> getRegisterEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(REGISTER_EVENT, transactionReceipt);
        ArrayList<RegisterEventResponse> responses = new ArrayList<RegisterEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RegisterEventResponse typedResponse = new RegisterEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<RegisterEventResponse> registerEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, RegisterEventResponse>() {
            @Override
            public RegisterEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(REGISTER_EVENT, log);
                RegisterEventResponse typedResponse = new RegisterEventResponse();
                typedResponse.log = log;
                typedResponse.owner = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<RegisterEventResponse> registerEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REGISTER_EVENT));
        return registerEventObservable(filter);
    }

    public List<LoginEventResponse> getLoginEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(LOGIN_EVENT, transactionReceipt);
        ArrayList<LoginEventResponse> responses = new ArrayList<LoginEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            LoginEventResponse typedResponse = new LoginEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<LoginEventResponse> loginEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, LoginEventResponse>() {
            @Override
            public LoginEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(LOGIN_EVENT, log);
                LoginEventResponse typedResponse = new LoginEventResponse();
                typedResponse.log = log;
                typedResponse.owner = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<LoginEventResponse> loginEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGIN_EVENT));
        return loginEventObservable(filter);
    }

    public List<ModifyEventResponse> getModifyEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(MODIFY_EVENT, transactionReceipt);
        ArrayList<ModifyEventResponse> responses = new ArrayList<ModifyEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ModifyEventResponse typedResponse = new ModifyEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ModifyEventResponse> modifyEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, ModifyEventResponse>() {
            @Override
            public ModifyEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(MODIFY_EVENT, log);
                ModifyEventResponse typedResponse = new ModifyEventResponse();
                typedResponse.log = log;
                typedResponse.owner = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<ModifyEventResponse> modifyEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(MODIFY_EVENT));
        return modifyEventObservable(filter);
    }

    public List<LogoutEventResponse> getLogoutEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(LOGOUT_EVENT, transactionReceipt);
        ArrayList<LogoutEventResponse> responses = new ArrayList<LogoutEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            LogoutEventResponse typedResponse = new LogoutEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<LogoutEventResponse> logoutEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, LogoutEventResponse>() {
            @Override
            public LogoutEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(LOGOUT_EVENT, log);
                LogoutEventResponse typedResponse = new LogoutEventResponse();
                typedResponse.log = log;
                typedResponse.owner = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<LogoutEventResponse> logoutEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGOUT_EVENT));
        return logoutEventObservable(filter);
    }

    public List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._receiver = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<TransferEventResponse> transferEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, TransferEventResponse>() {
            @Override
            public TransferEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFER_EVENT, log);
                TransferEventResponse typedResponse = new TransferEventResponse();
                typedResponse.log = log;
                typedResponse.owner = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._receiver = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<TransferEventResponse> transferEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
        return transferEventObservable(filter);
    }

    public static Identity load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Identity(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Identity load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Identity(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class RegisterEventResponse {
        public Log log;

        public String owner;
    }

    public static class LoginEventResponse {
        public Log log;

        public String owner;
    }

    public static class ModifyEventResponse {
        public Log log;

        public String owner;
    }

    public static class LogoutEventResponse {
        public Log log;

        public String owner;
    }

    public static class TransferEventResponse {
        public Log log;

        public String owner;

        public String _receiver;

        public BigInteger value;
    }
}
