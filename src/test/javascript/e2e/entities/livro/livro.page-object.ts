import { element, by, ElementFinder } from 'protractor';

export class LivroComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-livro div table .btn-danger'));
  title = element.all(by.css('jhi-livro div h2#page-heading span')).first();

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

export class LivroUpdatePage {
  pageTitle = element(by.id('jhi-livro-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  isbnInput = element(by.id('field_isbn'));
  nomeInput = element(by.id('field_nome'));
  editoraInput = element(by.id('field_editora'));
  capaInput = element(by.id('file_capa'));
  tomboInput = element(by.id('field_tombo'));
  autorSelect = element(by.id('field_autor'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIsbnInput(isbn: string): Promise<void> {
    await this.isbnInput.sendKeys(isbn);
  }

  async getIsbnInput(): Promise<string> {
    return await this.isbnInput.getAttribute('value');
  }

  async setNomeInput(nome: string): Promise<void> {
    await this.nomeInput.sendKeys(nome);
  }

  async getNomeInput(): Promise<string> {
    return await this.nomeInput.getAttribute('value');
  }

  async setEditoraInput(editora: string): Promise<void> {
    await this.editoraInput.sendKeys(editora);
  }

  async getEditoraInput(): Promise<string> {
    return await this.editoraInput.getAttribute('value');
  }

  async setCapaInput(capa: string): Promise<void> {
    await this.capaInput.sendKeys(capa);
  }

  async getCapaInput(): Promise<string> {
    return await this.capaInput.getAttribute('value');
  }

  async setTomboInput(tombo: string): Promise<void> {
    await this.tomboInput.sendKeys(tombo);
  }

  async getTomboInput(): Promise<string> {
    return await this.tomboInput.getAttribute('value');
  }

  async autorSelectLastOption(): Promise<void> {
    await this.autorSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async autorSelectOption(option: string): Promise<void> {
    await this.autorSelect.sendKeys(option);
  }

  getAutorSelect(): ElementFinder {
    return this.autorSelect;
  }

  async getAutorSelectedOption(): Promise<string> {
    return await this.autorSelect.element(by.css('option:checked')).getText();
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

export class LivroDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-livro-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-livro'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
