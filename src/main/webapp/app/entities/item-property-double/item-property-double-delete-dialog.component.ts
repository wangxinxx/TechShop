import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IItemPropertyDouble } from 'app/shared/model/item-property-double.model';
import { ItemPropertyDoubleService } from './item-property-double.service';

@Component({
    selector: 'jhi-item-property-double-delete-dialog',
    templateUrl: './item-property-double-delete-dialog.component.html'
})
export class ItemPropertyDoubleDeleteDialogComponent {
    itemPropertyDouble: IItemPropertyDouble;

    constructor(
        protected itemPropertyDoubleService: ItemPropertyDoubleService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.itemPropertyDoubleService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'itemPropertyDoubleListModification',
                content: 'Deleted an itemPropertyDouble'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-item-property-double-delete-popup',
    template: ''
})
export class ItemPropertyDoubleDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ itemPropertyDouble }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ItemPropertyDoubleDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.itemPropertyDouble = itemPropertyDouble;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/item-property-double', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/item-property-double', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
