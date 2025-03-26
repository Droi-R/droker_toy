package com.example.droi

//import what.the.rxkotlin.android.apis.ApiClient
//import what.the.rxkotlin.android.apis.ApiService
//import what.the.rxkotlin.android.data.UpdateItem
import com.bvc.appModule
import com.bvc.domain.usecase.GetUserUseCase
import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.FunSpec
import io.kotest.core.test.TestCase
import io.kotest.koin.KoinExtension
import kotlinx.coroutines.CoroutineScope
import org.koin.test.KoinTest
import org.koin.test.inject

class ExampleUnitTest : FunSpec() , KoinTest {

    override fun extensions() = listOf(KoinExtension(appModule))
    val getUserUseCase: GetUserUseCase by inject()
    val coroutineScope: CoroutineScope by inject()

    // 본 코드 기준으로 FunSpec형 Test를 시작하기 전에 1회만 실행
    override fun beforeSpec(spec: Spec) {
        super.beforeSpec(spec)
        println("beforeSpec #####")
//        apiService = ApiClient().getApiService()
    }

    // 개별 test()전마다 1회씩 실행
    override fun beforeTest(testCase: TestCase) {
        super.beforeTest(testCase)
        println("beforeTest >>>>>")
    }

    init {
        test("useCase API") {
            // awaitDone() 함수가 편리한 이유 :
            // test() 함수가 실행되는 스레드에서 onComplete() 함수를 호출할 때까지 기다려주기 때문
            getUserUseCase("shop",coroutineScope){

            }

//            val testObserver = yoRemoteDataSource.getYo("q")
//                .test()
//                .awaitDone(10000, TimeUnit.MILLISECONDS)
//            testObserver.assertNoErrors()
//                .assertComplete()
        }
//
//        test("updateUser API") {
//            val item = UpdateItem()
//            item.name = "Sample"
//            item.job = "Developer"
//
//            val testObserver = apiService.updateUser(999, item)
//                .test()
//                .awaitDone(2000, TimeUnit.MILLISECONDS)
//            testObserver.assertNoErrors()
//                .assertComplete()
//        }
//
//        test("createUser API") {
//            val item = UpdateItem()
//            item.name = "Sample2"
//            item.job = "Android Developer"
//
//            val testObserver = apiService.createUser(item)
//                .test()
//                .awaitDone(2000, TimeUnit.MILLISECONDS)
//            testObserver.assertNoErrors()
//                .assertComplete()
//        }
    }
}
