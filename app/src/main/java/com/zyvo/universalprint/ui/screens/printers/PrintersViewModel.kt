package com.zyvo.universalprint.ui.screens.printers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zyvo.universalprint.domain.model.Printer
import com.zyvo.universalprint.domain.model.PrinterType
import com.zyvo.universalprint.domain.repository.PrinterRepository
import com.zyvo.universalprint.printer.bluetooth.BluetoothPrinterConnector
import com.zyvo.universalprint.printer.core.PrinterConnector
import com.zyvo.universalprint.printer.network.NetworkPrinterConnector
import com.zyvo.universalprint.printer.usb.UsbPrinterConnector
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PrintersUiState(
    val printers: List<Printer> = emptyList(),
    val isDiscovering: Boolean = false,
    val error: String? = null,
    val showAddNetworkDialog: Boolean = false
)

@HiltViewModel
class PrintersViewModel @Inject constructor(
    private val repo: PrinterRepository,
    private val usb: UsbPrinterConnector,
    private val bt: BluetoothPrinterConnector,
    private val net: NetworkPrinterConnector
) : ViewModel() {
    private val _state = MutableStateFlow(PrintersUiState())
    val state: StateFlow<PrintersUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repo.observeAll().collect { list ->
                _state.update { it.copy(printers = list) }
            }
        }
    }

    fun discoverAll() {
        viewModelScope.launch {
            _state.update { it.copy(isDiscovering = true, error = null) }
            try {
                val found = usb.discover() + bt.discover()
                found.forEach { repo.save(it) }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message) }
            } finally {
                _state.update { it.copy(isDiscovering = false) }
            }
        }
    }

    fun addNetworkPrinter(name: String, address: String) {
        viewModelScope.launch {
            val p = Printer(id = "net_${System.currentTimeMillis()}", name = name, type = PrinterType.NETWORK, address = address)
            net.registerPrinter(p.id, address)
            repo.save(p)
            _state.update { it.copy(showAddNetworkDialog = false) }
        }
    }

    fun deletePrinter(id: String) {
        viewModelScope.launch { repo.delete(id) }
    }

    fun showAddNetworkDialog(show: Boolean) = _state.update { it.copy(showAddNetworkDialog = show) }
}
