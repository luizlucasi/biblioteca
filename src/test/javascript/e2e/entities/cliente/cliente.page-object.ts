import { element, by, ElementFinder } from 'protractor';

export class ClienteComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-cliente div table .btn-danger'));
  title = element.all(by.css('jhi-cliente div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class ClienteUpdatePage {
  pageTitle = element(by.id('jhi-cliente-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  codClienteInput = element(by.id('field_codCliente'));

  contratoSelect = element(by.id('field_contrato'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCodClienteInput(codCliente: string): Promise<void> {
    await this.codClienteInput.sendKeys(codCliente);
  }

  async getCodClienteInput(): Promise<string> {
    return await this.codClienteInput.getAttribute('value');
  }

  async contratoSelectLastOption(): Promise<void> {
    await this.contratoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async contratoSelectOption(option: string): Promise<void> {
    await this.contratoSelect.sendKeys(option);
  }

  getContratoSelect(): ElementFinder {
    return this.contratoSelect;
  }

  async getContratoSelectedOption(): Promise<string> {
    return await this.contratoSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class ClienteDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-cliente-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-cliente'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
