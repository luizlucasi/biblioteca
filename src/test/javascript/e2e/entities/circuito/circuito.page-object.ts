import { element, by, ElementFinder } from 'protractor';

export class CircuitoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-circuito div table .btn-danger'));
  title = element.all(by.css('jhi-circuito div h2#page-heading span')).first();
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

export class CircuitoUpdatePage {
  pageTitle = element(by.id('jhi-circuito-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idControleDesignacaoInput = element(by.id('field_idControleDesignacao'));
  idEstacaoInput = element(by.id('field_idEstacao'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdControleDesignacaoInput(idControleDesignacao: string): Promise<void> {
    await this.idControleDesignacaoInput.sendKeys(idControleDesignacao);
  }

  async getIdControleDesignacaoInput(): Promise<string> {
    return await this.idControleDesignacaoInput.getAttribute('value');
  }

  async setIdEstacaoInput(idEstacao: string): Promise<void> {
    await this.idEstacaoInput.sendKeys(idEstacao);
  }

  async getIdEstacaoInput(): Promise<string> {
    return await this.idEstacaoInput.getAttribute('value');
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

export class CircuitoDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-circuito-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-circuito'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
