import { Controller } from "@hotwired/stimulus"
import { Modal } from "bootstrap"

export default class extends Controller {
    connect() {
        this.modal = new Modal(this.element)
        this.modal.show()
    }

    hideBeforeRender(event) {
        if (this.isOpen()) {
            event.preventDefault()
            this.element.addEventListener('hidden.bs.toast', event.detail.resume)
            this.element.hide()
        }
    }

    isOpen() {
        return this.element.classList.contains("show")
    }
}