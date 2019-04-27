/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TechShopTestModule } from '../../../test.module';
import { ItemPropertyIntDeleteDialogComponent } from 'app/entities/item-property-int/item-property-int-delete-dialog.component';
import { ItemPropertyIntService } from 'app/entities/item-property-int/item-property-int.service';

describe('Component Tests', () => {
    describe('ItemPropertyInt Management Delete Component', () => {
        let comp: ItemPropertyIntDeleteDialogComponent;
        let fixture: ComponentFixture<ItemPropertyIntDeleteDialogComponent>;
        let service: ItemPropertyIntService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TechShopTestModule],
                declarations: [ItemPropertyIntDeleteDialogComponent]
            })
                .overrideTemplate(ItemPropertyIntDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ItemPropertyIntDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ItemPropertyIntService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
